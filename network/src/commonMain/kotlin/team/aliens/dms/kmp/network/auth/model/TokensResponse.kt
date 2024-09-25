package team.aliens.dms.kmp.network.auth.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokensResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("access_token_expired_at") val accessTokenExpiration: Instant,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("refresh_token_expired_at") val refreshTokenExpiration: Instant,
)
