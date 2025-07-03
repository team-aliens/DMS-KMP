package team.aliens.dms.kmp.core.network.votes.datasource

import team.aliens.dms.kmp.core.network.votes.model.request.GetVoteItemsRequest
import team.aliens.dms.kmp.core.network.votes.model.request.PostVoteRequest
import team.aliens.dms.kmp.core.network.votes.model.response.GetAllVotesResponse
import team.aliens.dms.kmp.core.network.votes.model.response.GetVoteItemsResponse

interface NetworkVotesDataSource {
    suspend fun getAllVotes(): Result<GetAllVotesResponse>
    suspend fun getVoteItems(request: GetVoteItemsRequest): Result<GetVoteItemsResponse>
    suspend fun postVote(request: PostVoteRequest): Result<Unit>
}
