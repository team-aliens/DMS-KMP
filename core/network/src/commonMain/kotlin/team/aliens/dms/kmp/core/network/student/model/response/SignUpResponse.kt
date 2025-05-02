package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val accessToken: String,
    val accessTokenExpiredAt: String,
    val refreshToken: String,
    val refreshTokenExpiration: String,
    val features: Features,
) {
    @Serializable
    data class Features(
        val mealService: Boolean,
        val noticeService: Boolean,
        val pointService: Boolean,
        val studyRoomService: Boolean,
        val remainsService: Boolean,
    )
}
