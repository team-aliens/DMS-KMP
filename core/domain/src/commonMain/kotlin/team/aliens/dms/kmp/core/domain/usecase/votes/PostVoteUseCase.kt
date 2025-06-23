package team.aliens.dms.kmp.core.domain.usecase.votes

import team.aliens.dms.kmp.core.data.votes.repository.VotesRepository

class PostVoteUseCase(
    private val votesRepository: VotesRepository,
) {
    suspend operator fun invoke(
        votingTopic: String,
        selectId: String,
    ) = votesRepository.postVote(
        votingTopic = votingTopic,
        selectId = selectId,
    )
}
