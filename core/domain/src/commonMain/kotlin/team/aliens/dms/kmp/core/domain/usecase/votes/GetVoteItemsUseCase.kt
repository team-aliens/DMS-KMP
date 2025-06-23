package team.aliens.dms.kmp.core.domain.usecase.votes

import team.aliens.dms.kmp.core.data.votes.repository.VotesRepository

class GetVoteItemsUseCase(
    private val votesRepository: VotesRepository,
) {
    suspend operator fun invoke(votingTopicId: String) =
        votesRepository.getVoteItems(votingTopicId = votingTopicId)
}
