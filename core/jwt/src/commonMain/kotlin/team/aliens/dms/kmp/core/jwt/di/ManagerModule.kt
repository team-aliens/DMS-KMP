package team.aliens.dms.kmp.core.jwt.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.accept
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.parameters
import org.koin.dsl.module
import team.aliens.dms.kmp.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.kmp.core.jwt.model.toModel
import team.aliens.dms.kmp.core.jwt.network.IgnoreRequests
import team.aliens.dms.kmp.core.jwt.network.exception.CannotReissueTokenException
import team.aliens.dms.kmp.core.network.PlatformConfig
import team.aliens.dms.kmp.network.auth.model.TokensResponse

internal val managerModule = module {
    single {
        HttpClient(CIO) {
            install("authInterceptor") {
                requestPipeline.intercept(HttpRequestPipeline.Before) {
                    fun isShouldBeIgnored(): Boolean {
                        val ignoreRequests: IgnoreRequests = get()
                        return ignoreRequests.requests.any { ignoreRequest ->
                            context.url.encodedPath.contains(ignoreRequest.path) && context.method == ignoreRequest.method
                        }
                    }

                    if (isShouldBeIgnored()) {
                        val jwtDataStoreDataSource: JwtDataStoreDataSource = get()
                        val accessToken = jwtDataStoreDataSource.loadTokens().accessToken.value
                        context.headers.append(HttpHeaders.Authorization, "Bearer $accessToken")
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

            install(Auth) {
                bearer {
                    loadTokens {
                        val jwtDataStoreDataSource: JwtDataStoreDataSource = get()
                        val tokens = jwtDataStoreDataSource.loadTokens()
                        val accessToken = tokens.accessToken.value
                        val refreshToken = tokens.refreshToken.value

                        BearerTokens(
                            accessToken = accessToken,
                            refreshToken = refreshToken,
                        )
                    }

                    refreshTokens {
                        val jwtDataStoreDataSource: JwtDataStoreDataSource = get()
                        val refreshToken = jwtDataStoreDataSource.loadTokens().refreshToken.value

                        val response = client.submitForm(
                            url = "/auth/reissue",
                            formParameters = parameters {
                                append(
                                    name = "refresh-token",
                                    value = refreshToken,
                                )
                            },
                        ) { markAsRefreshTokenRequest() }

                        return@refreshTokens if (response.status.isSuccess()) {
                            val refreshTokenInfo: TokensResponse = response.body()
                            jwtDataStoreDataSource.storeTokens(refreshTokenInfo.toModel())

                            BearerTokens(
                                accessToken = refreshTokenInfo.accessToken,
                                refreshToken = refreshTokenInfo.refreshToken,
                            )
                        } else {
                            throw CannotReissueTokenException()
                        }
                    }
                }
            }
        }
    }
}
