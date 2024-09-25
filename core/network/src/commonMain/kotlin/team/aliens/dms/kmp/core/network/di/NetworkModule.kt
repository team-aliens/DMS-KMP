package team.aliens.dms.kmp.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.jwt.JwtDataSource
import team.aliens.dms.kmp.core.datastore.jwt.model.AccessToken
import team.aliens.dms.kmp.core.datastore.jwt.model.RefreshToken
import team.aliens.dms.kmp.core.datastore.jwt.model.Tokens
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.network.auth.model.TokensResponse

val networkModule =
    module {
        single {
            HttpClient {
                install(Auth) {
                    bearer {
                        loadTokens {
                            val jwtDataStore: JwtDataSource = get()
                            val accessToken = jwtDataStore.loadTokens().accessToken.value
                            val refreshToken = jwtDataStore.loadTokens().refreshToken.value

                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            )
                        }

                        refreshTokens {
                            val jwtDataSource: JwtDataSource = get()

                            val refreshTokenInfo: TokensResponse = client.submitForm(
                                url = "${PlatformConfig.baseUrl}/auth/reissue",
                                formParameters = parameters {
                                    append(
                                        name = "refresh-token",
                                        value = jwtDataSource.loadTokens().refreshToken.value,
                                    )
                                },
                            ) { markAsRefreshTokenRequest() }.body()

                            jwtDataSource.storeTokens(
                                Tokens(
                                    accessToken = AccessToken(
                                        value = refreshTokenInfo.accessToken,
                                        expiration = LocalDateTime.parse(refreshTokenInfo.accessTokenExpiration),
                                    ),
                                    refreshToken = RefreshToken(
                                        value = refreshTokenInfo.refreshToken,
                                        expiration = LocalDateTime.parse(refreshTokenInfo.refreshTokenExpiration),
                                    ),
                                ),
                            )
                            val accessToken = refreshTokenInfo.accessToken
                            val refreshToken = refreshTokenInfo.refreshToken

                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            )
                        }
                    }
                }

                defaultRequest {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    url {
                        protocol = URLProtocol.HTTPS
                        host = PlatformConfig.baseUrl
                    }
                }

                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        },
                    )
                }

                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                logger.log("Logger Ktor => $message")
                            }
                        }
                    level = LogLevel.ALL
                }

                install(ResponseObserver) {
                    onResponse { response ->
                        logger.info("HTTP status: ${response.status.value}")
                    }
                }
            }
        }
    }
