package team.aliens.dms.kmp.core.domain.usecase.points

import team.aliens.dms.kmp.core.data.points.repository.PointsRepository
import team.aliens.dms.kmp.core.model.type.PointType

class GetPointsUseCase(
    private val pointsRepository: PointsRepository,
) {
    suspend operator fun invoke(type: PointType?, page: Long?,size: Long?) = pointsRepository.getPoints(
        type = type, page = page, size = size
    )
}
