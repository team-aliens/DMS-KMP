package team.aliens.dms.kmp.core.network.remains.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetAppliedRemainsOptionResponse(
    val id: String,
    val title: String,
)
