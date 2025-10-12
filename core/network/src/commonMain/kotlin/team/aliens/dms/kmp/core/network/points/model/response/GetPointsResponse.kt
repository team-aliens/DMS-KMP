package team.aliens.dms.kmp.core.network.points.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.points.model.dto.PointHistoryDto

@Serializable
data class GetPointsResponse (
    val totalPoint: Int,
    val pointHistories: List<PointHistoryDto>,
)
