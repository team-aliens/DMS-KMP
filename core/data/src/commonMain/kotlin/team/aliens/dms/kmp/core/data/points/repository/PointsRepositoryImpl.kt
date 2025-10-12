package team.aliens.dms.kmp.core.data.points.repository

import team.aliens.dms.kmp.core.data.points.mapper.toDto
import team.aliens.dms.kmp.core.data.points.mapper.toModel
import team.aliens.dms.kmp.core.model.points.PointHistoryModel
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.core.network.points.datasource.NetworkPointsDataSource
import team.aliens.dms.kmp.core.network.points.model.request.GetPointsRequest

internal class PointsRepositoryImpl(
    private val networkPointsDataSource: NetworkPointsDataSource,
) : PointsRepository {
    override suspend fun getPoints(
        type: PointType?,
        page: Long?,
        size: Long?,
    ): Result<PointHistoryModel> = networkPointsDataSource.getPoints(
        request = GetPointsRequest(
            query = GetPointsRequest.Query(
                type = type?.toDto(),
                page = page,
                size = size,
            ),
        ),
    ).map { it.toModel() }
}
