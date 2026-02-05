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
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import kotlinx.serialization.modules.SerializersModule
import org.koin.dsl.module
import team.aliens.dms.kmp.core.common.exception.UnknownException
import team.aliens.dms.kmp.core.common.exception.network.BadRequestException
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.common.exception.network.ForbiddenException
import team.aliens.dms.kmp.core.common.exception.network.InternalServerErrorException
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.RequestTimeoutException
import team.aliens.dms.kmp.core.common.exception.network.ServiceUnavailableException
import team.aliens.dms.kmp.core.common.exception.network.TooManyRequestsException
import team.aliens.dms.kmp.core.common.exception.network.UnAuthorizedException
import team.aliens.dms.kmp.core.common.exception.network.UnprocessableEntityException
import team.aliens.dms.kmp.core.common.exception.network.UnsupportedMediaTypeException
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource
import team.aliens.dms.kmp.core.model.auth.AccessToken
import team.aliens.dms.kmp.core.model.auth.RefreshToken
import team.aliens.dms.kmp.core.model.auth.TokenModel
import team.aliens.dms.kmp.core.network.IgnoreRequests
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.core.network.auth.model.TokensResponse
import team.aliens.dms.kmp.core.network.exception.CannotReissueTokenException

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
                        explicitNulls = false
                    },
                )
            }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Logger.SIMPLE.log(message)
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
                    if (!response.status.isSuccess()) {
                        when (response.status) {
                            HttpStatusCode.BadRequest -> throw BadRequestException()
                            HttpStatusCode.Unauthorized -> throw UnAuthorizedException()
                            HttpStatusCode.Forbidden -> throw ForbiddenException()
                            HttpStatusCode.UnprocessableEntity -> throw UnprocessableEntityException()
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
                            authPreferencesDataSource.loadTokens().getOrNull()?.accessToken?.value
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
/*                    loadTokens {
                        val authPreferencesDataSource: AuthPreferencesDataSource = get()
                        return@loadTokens authPreferencesDataSource.loadTokens().getOrNull()
                            ?.let { tokens ->
                                BearerTokens(
                                    accessToken = tokens.accessToken.value,
                                    refreshToken = tokens.refreshToken.value,
                                )
                            }
                    }*/

                    refreshTokens {
                        val authPreferencesDataSource: AuthPreferencesDataSource = get()
                        val tokensResult = authPreferencesDataSource.loadTokens()
                        if (tokensResult.isFailure) return@refreshTokens null
                        val tokens = tokensResult.getOrNull() ?: return@refreshTokens null
                        val refreshToken = tokens.refreshToken.value

                        return@refreshTokens kotlin.runCatching {
                            // TODO: refresh 작동 재확인 필요
                            val response = client.put("/auth/reissue") {
                                header(key = "refresh-token", value = refreshToken)
                                markAsRefreshTokenRequest()
                            }
//                            val response = client.submitForm(
//                                url = "/auth/reissue",
//                                formParameters = parameters {
//                                    append(
//                                        name = "refresh-token",
//                                        value = refreshToken,
//                                    )
//                                },
//                            ) { markAsRefreshTokenRequest() }

                            if (!response.status.isSuccess()) {
                                throw CannotReissueTokenException()
                            }

                            val tokensResponse: TokensResponse = response.body()
                            authPreferencesDataSource.storeTokens(
                                TokenModel(
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
                        }.onFailure {
                            logger.error("Token refresh failed: ${it.message}")
                        }.getOrNull()
                    }
                }
            }
        }
    }

    includes(ignoreRequestModule)
}
