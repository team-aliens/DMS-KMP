package team.aliens.dms.kmp.core.network.student.model.request

data class GetStudentsRequest(
    val query: Query,
) {
    data class Query(
        val name: String,
    )
}
