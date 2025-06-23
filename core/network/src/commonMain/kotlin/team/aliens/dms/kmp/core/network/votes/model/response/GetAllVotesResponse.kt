package team.aliens.dms.kmp.core.network.votes.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.votes.model.dto.VoteDto

@Serializable
data class GetAllVotesResponse(
    val votingTopics: List<VoteDto>,
)
