package team.aliens.dms.kmp.core.jwt.di

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import org.koin.dsl.module
import team.aliens.dms.kmp.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.kmp.core.jwt.model.AccessToken
import team.aliens.dms.kmp.core.jwt.model.RefreshToken
import team.aliens.dms.kmp.core.jwt.model.Tokens
import team.aliens.dms.kmp.network.auth.model.TokensResponse

internal val managerModule = module {
    single {
        HttpClient {
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

                        val refreshTokenInfo: TokensResponse = client.submitForm(
                            url = "/auth/reissue",
                            formParameters = parameters {
                                append(
                                    name = "refresh-token",
                                    value = refreshToken,
                                )
                            },
                        ) { markAsRefreshTokenRequest() }.body()

                        jwtDataStoreDataSource.storeTokens(
                            Tokens(
                                accessToken = AccessToken(
                                    value = refreshTokenInfo.accessToken,
                                    expiration = refreshTokenInfo.accessTokenExpiration,
                                ),
                                refreshToken = RefreshToken(
                                    value = refreshTokenInfo.refreshToken,
                                    expiration = refreshTokenInfo.refreshTokenExpiration,
                                ),
                            ),
                        )

                        BearerTokens(
                            accessToken = refreshTokenInfo.accessToken,
                            refreshToken = refreshTokenInfo.refreshToken,
                        )
                    }
                }
            }
        }
    }
}
