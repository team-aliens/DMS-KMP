package team.aliens.dms.kmp.core.data.points.repository

import team.aliens.dms.kmp.core.model.points.PointHistoryModel
import team.aliens.dms.kmp.core.model.type.PointType

interface PointsRepository {
    suspend fun getPoints(
        type: PointType?,
        page: Long?,
        size: Long?,
    ): Result<PointHistoryModel>
}
