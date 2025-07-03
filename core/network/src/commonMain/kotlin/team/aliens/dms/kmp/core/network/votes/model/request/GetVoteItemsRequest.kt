package team.aliens.dms.kmp.core.network.votes.model.request

data class GetVoteItemsRequest(
    val path: Path,
) {
    data class Path(
        val votingTopicId: String,
    )
}
