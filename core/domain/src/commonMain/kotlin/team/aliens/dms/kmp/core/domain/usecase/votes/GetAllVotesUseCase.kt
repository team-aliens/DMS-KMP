package team.aliens.dms.kmp.core.domain.usecase.votes

import team.aliens.dms.kmp.core.data.votes.repository.VotesRepository

class GetAllVotesUseCase(
    private val votesRepository: VotesRepository,
) {
    suspend operator fun invoke() = votesRepository.getAllVotes()
}
