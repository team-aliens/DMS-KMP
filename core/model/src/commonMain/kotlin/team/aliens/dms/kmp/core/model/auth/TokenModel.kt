package team.aliens.dms.kmp.core.model.auth

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
    override val expiration: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
) : Token()

data class RefreshToken(
    override val value: String = "",
    override val expiration: LocalDateTime = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
) : Token()
