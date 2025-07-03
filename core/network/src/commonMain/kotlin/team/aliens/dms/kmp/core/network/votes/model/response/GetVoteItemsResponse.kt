package team.aliens.dms.kmp.core.network.votes.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.votes.model.dto.VoteItemDto

@Serializable
data class GetVoteItemsResponse(
    val votingOptions: List<VoteItemDto>,
)
