package team.aliens.dms.kmp.core.network.student.model.request

data class ExamineStudentNumberRequest(
    val query: Query,
) {
    data class Query(
        val schoolId: String,
        val grade: Int,
        val classroom: Int,
        val number: Int,
    )
}
