package team.aliens.dms.kmp.core.datastore.jwt.model

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.network.auth.model.TokensResponse

data class Tokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)

sealed class Token {
    abstract val value: String
    abstract val expiration: LocalDateTime
}

data class AccessToken(
    override val value: String,
    override val expiration: LocalDateTime,
) : Token()

data class RefreshToken(
    override val value: String,
    override val expiration: LocalDateTime,
) : Token()

internal fun TokensResponse.toModel(): Tokens = Tokens(
    accessToken = AccessToken(
        value = this.accessToken,
        expiration = LocalDateTime.parse(this.accessTokenExpiration),
    ),
    refreshToken = RefreshToken(
        value = this.refreshToken,
        expiration = LocalDateTime.parse(this.refreshTokenExpiration),
    ),
)
