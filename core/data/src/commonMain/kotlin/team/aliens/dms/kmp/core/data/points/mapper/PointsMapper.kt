package team.aliens.dms.kmp.core.data.points.mapper

import team.aliens.dms.kmp.core.model.points.PointHistoryModel
import team.aliens.dms.kmp.core.model.points.PointModel
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.core.network.points.model.dto.PointHistoryDto
import team.aliens.dms.kmp.core.network.points.model.dto.PointTypeDto
import team.aliens.dms.kmp.core.network.points.model.response.GetPointsResponse

internal fun PointType.toDto() =
    when (this) {
        PointType.ALL -> PointTypeDto.ALL
        PointType.BONUS -> PointTypeDto.BONUS
        PointType.MINUS -> PointTypeDto.MINUS
    }

internal fun GetPointsResponse.toModel() =
    PointHistoryModel(
        totalPoint = totalPoint,
        pointHistories = pointHistories.map { it.toModel() },
    )

private fun PointHistoryDto.toModel() =
    PointModel(
        id = pointHistoryId,
        date = date,
        type = type.toModel(),
        name = name,
        score = score,
    )

internal fun PointTypeDto.toModel() =
    when (this) {
        PointTypeDto.ALL -> PointType.ALL
        PointTypeDto.BONUS -> PointType.BONUS
        PointTypeDto.MINUS -> PointType.MINUS
    }
