package team.aliens.dms.kmp.core.datastore.jwt.model

import kotlinx.datetime.Instant
import team.aliens.dms.kmp.network.auth.model.TokensResponse

data class Tokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)

sealed class Token {
    abstract val value: String
    abstract val expiration: Instant
}

data class AccessToken(
    override val value: String,
    override val expiration: Instant,
) : Token()

data class RefreshToken(
    override val value: String,
    override val expiration: Instant,
) : Token()

internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = this.accessTokenExpiration,
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = this.refreshTokenExpiration,
    ),
)
