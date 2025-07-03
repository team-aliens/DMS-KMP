package team.aliens.dms.kmp.core.model.votes

import kotlinx.serialization.Serializable

@Serializable
data class VoteItemModel(
    val id: String,
    val votingOptionName: String,
)
