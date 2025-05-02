package team.aliens.dms.kmp.core.network.student.model.request

data class CheckEmailDuplicationRequest(
    val query: Query,
) {
    data class Query(
        val email: String,
    )
}
