package team.aliens.dms.kmp.core.model.points

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.model.type.PointType

data class PointModel(
    val id: String,
    val date: LocalDate,
    val type: PointType,
    val name: String,
    val score: Int,
)
