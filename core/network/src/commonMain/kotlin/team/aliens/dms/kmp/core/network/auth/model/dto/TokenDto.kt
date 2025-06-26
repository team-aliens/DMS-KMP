package team.aliens.dms.kmp.core.network.auth.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: FeatureDto,
)
