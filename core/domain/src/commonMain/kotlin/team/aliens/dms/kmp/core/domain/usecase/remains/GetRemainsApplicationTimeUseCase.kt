package team.aliens.dms.kmp.core.domain.usecase.remains

import team.aliens.dms.kmp.core.data.remains.repository.RemainsRepository

class GetRemainsApplicationTimeUseCase(
    private val remainsRepository: RemainsRepository
) {
    suspend operator fun invoke() = remainsRepository.getRemainsApplicationTime()
}
