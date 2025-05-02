package team.aliens.dms.kmp.core.network.student.model.request

data class FindIdRequest(
    val path: Path,
    val query: Query,
) {
    data class Path(
        val schoolId: String,
    )

    data class Query(
        val name: String,
        val grade: Int,
        val classRoom: Int,
        val number: Int,
    )
}
