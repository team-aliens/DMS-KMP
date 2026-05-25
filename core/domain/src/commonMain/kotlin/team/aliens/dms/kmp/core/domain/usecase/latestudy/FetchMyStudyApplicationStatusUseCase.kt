package team.aliens.dms.kmp.core.domain.usecase.latestudy

import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository

class FetchMyStudyApplicationStatusUseCase(
    private val lateStudyRepository: LateStudyRepository,
) {
    suspend operator fun invoke() =
        runCatching {
            lateStudyRepository.fetchMyStudyApplicationStatus()
        }
}
