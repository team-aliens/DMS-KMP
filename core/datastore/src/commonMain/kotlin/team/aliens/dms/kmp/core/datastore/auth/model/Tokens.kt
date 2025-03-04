package team.aliens.dms.kmp.core.datastore.auth.model

import kotlinx.datetime.LocalDateTime

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
