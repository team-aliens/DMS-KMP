package team.aliens.dms.kmp.core.network.votes.model.request

data class PostVoteRequest(
    val path: Path,
    val query: Query,
) {
    data class Path(
        val votingTopic: String,
    )
    data class Query(
        val selectId: String,
    )
}
