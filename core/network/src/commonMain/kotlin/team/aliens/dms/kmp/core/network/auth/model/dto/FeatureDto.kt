package team.aliens.dms.kmp.core.network.auth.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDto(
    val mealService: Boolean,
    val noticeService: Boolean,
    val pointService: Boolean,
    val studyRoomService: Boolean,
    val remainService: Boolean,
)
