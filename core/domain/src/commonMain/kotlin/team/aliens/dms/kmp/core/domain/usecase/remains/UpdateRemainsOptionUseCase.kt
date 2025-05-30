package team.aliens.dms.kmp.core.domain.usecase.remains

import team.aliens.dms.kmp.core.data.remains.repository.RemainsRepository

class UpdateRemainsOptionUseCase(
    private val remainsRepository: RemainsRepository,
) {
    suspend operator fun invoke(remainOptionId: String) =
        remainsRepository.updateRemainsOption(remainOptionId = remainOptionId)
}
