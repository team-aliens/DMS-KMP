package team.aliens.dms.kmp.core.network.auth.model.request

data class CheckIdExistsRequest(
    val query: Query,
) {
    data class Query(
        val accountId: String,
    )
}
