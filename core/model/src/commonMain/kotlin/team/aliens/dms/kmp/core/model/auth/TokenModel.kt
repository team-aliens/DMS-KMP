package team.aliens.dms.kmp.core.model.auth

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.util.now

data class TokenModel(
    val accessToken: AccessToken = AccessToken(),
    val refreshToken: RefreshToken = RefreshToken(),
)

sealed class Token {
    abstract val value: String
    abstract val expiration: LocalDateTime
}

data class AccessToken(
    override val value: String = "",
    override val expiration: LocalDateTime = now,
) : Token()

data class RefreshToken(
    override val value: String = "",
    override val expiration: LocalDateTime = now,
) : Token()
