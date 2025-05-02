package team.aliens.dms.kmp.core.network.student.model.request

data class CheckIdDuplicationRequest(
    val query: Query,
) {
    data class Query(
        val id: String,
    )
}
