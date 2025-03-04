package team.aliens.dms.kmp.core.network.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiredAt: String,
    val features: Features,
) {
    @Serializable
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainService: Boolean,
    )
}
