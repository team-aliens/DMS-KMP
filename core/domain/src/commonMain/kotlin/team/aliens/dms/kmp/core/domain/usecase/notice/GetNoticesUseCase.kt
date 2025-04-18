package team.aliens.dms.kmp.core.domain.usecase.notice

import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepository
import team.aliens.dms.kmp.core.model.type.OrderType

class GetNoticesUseCase(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(orderType: OrderType) =
        noticeRepository.getNotices(orderType = orderType)
}
