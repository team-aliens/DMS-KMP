package team.aliens.dms.kmp.core.network.points.model.dto

import kotlinx.serialization.Serializable

@Serializable
enum class PointTypeDto {
    ALL,
    BONUS,
    MINUS,
}
