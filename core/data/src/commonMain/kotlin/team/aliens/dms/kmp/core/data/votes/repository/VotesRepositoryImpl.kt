package team.aliens.dms.kmp.core.data.votes.repository

import team.aliens.dms.kmp.core.data.votes.mapper.toModel
import team.aliens.dms.kmp.core.model.votes.VoteItemModel
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.core.network.votes.datasource.NetworkVotesDataSource
import team.aliens.dms.kmp.core.network.votes.model.request.GetVoteItemsRequest
import team.aliens.dms.kmp.core.network.votes.model.request.PostVoteRequest

internal class VotesRepositoryImpl(
    private val networkVotesDataSource: NetworkVotesDataSource,
) : VotesRepository {
    override suspend fun getAllVotes(): Result<List<VoteModel>> =
        networkVotesDataSource.getAllVotes().map { it.toModel() }

    override suspend fun getVoteItems(votingTopicId: String): Result<List<VoteItemModel>> =
        networkVotesDataSource.getVoteItems(
            request = GetVoteItemsRequest(
                path = GetVoteItemsRequest.Path(votingTopicId = votingTopicId),
            ),
        ).map { it.toModel() }

    override suspend fun postVote(votingTopic: String, selectId: String): Result<Unit> =
        networkVotesDataSource.postVote(
            request = PostVoteRequest(
                path = PostVoteRequest.Path(
                    votingTopic = votingTopic,
                ),
                query = PostVoteRequest.Query(selectId = selectId),
            ),
        ).map { }
}
