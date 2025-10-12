package team.aliens.dms.kmp.core.network.points.datasource

import team.aliens.dms.kmp.core.network.points.model.request.GetPointsRequest
import team.aliens.dms.kmp.core.network.points.model.response.GetPointsResponse

interface NetworkPointsDataSource {
    suspend fun getPoints(request: GetPointsRequest): Result<GetPointsResponse>
}
