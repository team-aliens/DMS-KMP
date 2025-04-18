package team.aliens.dms.kmp.core.domain.usecase.notice

import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepository

class GetNoticeDetailUseCase(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke(noticeId: String) =
        noticeRepository.getNoticeDetail(noticeId = noticeId)
}
