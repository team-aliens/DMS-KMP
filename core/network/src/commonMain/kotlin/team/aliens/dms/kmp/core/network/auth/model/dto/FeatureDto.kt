package team.aliens.dms.kmp.core.network.auth.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class FeatureDto(
    val mealService: Boolean = false,
    val noticeService: Boolean = false,
    val pointService: Boolean = false,
    val remainService: Boolean = false,
)
