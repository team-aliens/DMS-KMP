package team.aliens.dms.kmp.core.network.votes.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import team.aliens.dms.kmp.core.network.votes.model.request.GetVoteItemsRequest
import team.aliens.dms.kmp.core.network.votes.model.request.PostVoteRequest
import team.aliens.dms.kmp.core.network.votes.model.response.GetAllVotesResponse
import team.aliens.dms.kmp.core.network.votes.model.response.GetVoteItemsResponse

internal class KtorVotesDataSource(
    private val client: HttpClient,
) : NetworkVotesDataSource {
    override suspend fun getAllVotes(): Result<GetAllVotesResponse> = kotlin.runCatching {
        client.get("/votes").body()
    }

    override suspend fun getVoteItems(request: GetVoteItemsRequest): Result<GetVoteItemsResponse> =
        kotlin.runCatching {
            client.get("/votes/option/${request.path.votingTopicId}").body()
        }

    override suspend fun postVote(request: PostVoteRequest): Result<Unit> = kotlin.runCatching {
        client.post("/votes/student/${request.path.votingTopic}") {
            parameter("selected-id", request.query.selectId)
        }.body()
    }
}
