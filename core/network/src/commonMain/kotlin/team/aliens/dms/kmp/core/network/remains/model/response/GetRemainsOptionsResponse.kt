package team.aliens.dms.kmp.core.network.remains.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetRemainsOptionsResponse(
    val remainOptions: List<RemainOption>,
) {
    @Serializable
    data class RemainOption(
        val id: String,
        val title: String,
        val description: String,
        val isApplied: Boolean,
    )
}
