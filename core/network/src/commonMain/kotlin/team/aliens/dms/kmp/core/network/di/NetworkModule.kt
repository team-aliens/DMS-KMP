package team.aliens.dms.kmp.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.accept
import io.ktor.client.request.forms.submitForm
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.modules.SerializersModule
import org.koin.dsl.module
import team.aliens.dms.kmp.core.common.exception.UnknownException
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource
import team.aliens.dms.kmp.core.datastore.auth.model.AccessToken
import team.aliens.dms.kmp.core.datastore.auth.model.RefreshToken
import team.aliens.dms.kmp.core.datastore.auth.model.Tokens
import team.aliens.dms.kmp.core.network.IgnoreRequests
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.core.network.auth.model.TokensResponse
import team.aliens.dms.kmp.core.network.exception.BadRequestException
import team.aliens.dms.kmp.core.network.exception.CannotReissueTokenException
import team.aliens.dms.kmp.core.network.exception.ConflictException
import team.aliens.dms.kmp.core.network.exception.ForbiddenException
import team.aliens.dms.kmp.core.network.exception.InternalServerErrorException
import team.aliens.dms.kmp.core.network.exception.NotFoundException
import team.aliens.dms.kmp.core.network.exception.RequestTimeoutException
import team.aliens.dms.kmp.core.network.exception.ServiceUnavailableException
import team.aliens.dms.kmp.core.network.exception.TooManyRequestsException
import team.aliens.dms.kmp.core.network.exception.UnAuthorizedException
import team.aliens.dms.kmp.core.network.exception.UnsupportedMediaTypeException

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        namingStrategy = JsonNamingStrategy.SnakeCase
                        serializersModule = SerializersModule { }
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    },
                )
            }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            println("Logger Ktor => $message")
                        }
                    }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    logger.info("HTTP status: ${response.status.value}")
                }
            }

            HttpResponseValidator {
                validateResponse { response ->
                    when (response.status) {
                        HttpStatusCode.BadRequest -> throw BadRequestException()
                        HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
                        HttpStatusCode.Forbidden -> throw ForbiddenException()
                        HttpStatusCode.NotFound -> throw NotFoundException()
                        HttpStatusCode.RequestTimeout -> throw RequestTimeoutException()
                        HttpStatusCode.Conflict -> throw ConflictException()
                        HttpStatusCode.UnsupportedMediaType -> throw UnsupportedMediaTypeException()
                        HttpStatusCode.TooManyRequests -> throw TooManyRequestsException()
                        HttpStatusCode.InternalServerError -> throw InternalServerErrorException()
                        HttpStatusCode.ServiceUnavailable -> throw ServiceUnavailableException()
                        else -> throw UnknownException()
                    }
                }

                handleResponseExceptionWithRequest { cause, request ->
                    throw cause
                }
            }

            // auth
            install("authInterceptor") {
                requestPipeline.intercept(HttpRequestPipeline.Before) {
                    fun isShouldBeIgnored(): Boolean {
                        val ignoreRequests: IgnoreRequests = get()
                        return ignoreRequests.requests.any { ignoreRequest ->
                            context.url.encodedPath.contains(ignoreRequest.path) && context.method == ignoreRequest.method
                        }
                    }

                    if (!isShouldBeIgnored()) {
                        val authPreferencesDataSource: AuthPreferencesDataSource = get()
                        val accessToken =
                            authPreferencesDataSource.loadTokens().getOrNull()?.accessToken
                        context.headers.append(HttpHeaders.Authorization, "Bearer $accessToken")
                    }
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTPS
                    host = PlatformConfig.baseUrl
                }
            }

            install(Auth) {
                bearer {
                    // TODO: loadTokens null 처리 필요
                    loadTokens {
                        val authPreferencesDataSource: AuthPreferencesDataSource = get()
                        return@loadTokens authPreferencesDataSource.loadTokens().getOrNull()
                            ?.let { tokens ->
                                BearerTokens(tokens.accessToken.value, tokens.refreshToken.value)
                            }
                    }

                    refreshTokens {
                        val authPreferencesDataSource: AuthPreferencesDataSource = get()
                        val refreshToken =
                            authPreferencesDataSource.loadTokens().getOrThrow()?.refreshToken?.value

                        kotlin.runCatching {
                            val response = client.submitForm(
                                url = "/auth/reissue",
                                formParameters = parameters {
                                    append(
                                        name = "refresh-token",
                                        value = refreshToken ?: return@parameters,
                                    )
                                },
                            ) { markAsRefreshTokenRequest() }

                            if (!response.status.isSuccess()) {
                                throw CannotReissueTokenException()
                            }

                            val tokensResponse: TokensResponse = response.body()
                            authPreferencesDataSource.storeTokens(
                                Tokens(
                                    accessToken = AccessToken(
                                        value = tokensResponse.accessToken,
                                        expiration = LocalDateTime.parse(tokensResponse.accessTokenExpiration),
                                    ),
                                    refreshToken = RefreshToken(
                                        value = tokensResponse.refreshToken,
                                        expiration = LocalDateTime.parse(tokensResponse.refreshTokenExpiration),
                                    ),
                                ),
                            )

                            BearerTokens(
                                accessToken = tokensResponse.accessToken,
                                refreshToken = tokensResponse.refreshToken,
                            )
                        }.getOrNull()
                    }
                }
            }
        }
    }

    includes(ignoreRequestModule)
}
