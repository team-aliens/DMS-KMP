package team.aliens.dms.kmp.core.domain.usecase.notice

import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepository

class GetWhetherNewNoticesExistUseCase(
    private val noticeRepository: NoticeRepository,
) {
    suspend operator fun invoke() = noticeRepository.getWhetherNewNoticesExist()
}
