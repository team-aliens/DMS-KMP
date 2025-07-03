package team.aliens.dms.kmp.core.data.votes.repository

import team.aliens.dms.kmp.core.model.votes.VoteItemModel
import team.aliens.dms.kmp.core.model.votes.VoteModel

interface VotesRepository {
    suspend fun getAllVotes(): Result<List<VoteModel>>
    suspend fun getVoteItems(votingTopicId: String): Result<List<VoteItemModel>>
    suspend fun postVote(
        votingTopic: String,
        selectId: String,
    ): Result<Unit>
}
