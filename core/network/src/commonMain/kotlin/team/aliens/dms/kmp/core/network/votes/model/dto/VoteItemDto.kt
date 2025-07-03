package team.aliens.dms.kmp.core.network.votes.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class VoteItemDto(
    val id: String,
    val votingOptionName: String,
)
