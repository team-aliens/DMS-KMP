package team.aliens.dms.kmp.core.network.points.model.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class PointHistoryDto(
    val pointHistoryId: String,
    val date: LocalDate,
    val type: PointTypeDto,
    val name: String,
    val score: Int,
)
