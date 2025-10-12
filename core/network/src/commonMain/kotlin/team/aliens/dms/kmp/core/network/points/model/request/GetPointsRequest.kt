package team.aliens.dms.kmp.core.network.points.model.request

import team.aliens.dms.kmp.core.network.points.model.dto.PointTypeDto

data class GetPointsRequest(
    val query: Query,
) {
    data class Query(
        val type: PointTypeDto?,
        val page: Long?,
        val size: Long?,
    )
}
