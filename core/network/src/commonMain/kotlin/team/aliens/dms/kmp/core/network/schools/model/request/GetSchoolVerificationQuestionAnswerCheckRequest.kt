package team.aliens.dms.kmp.core.network.schools.model.request

data class GetSchoolVerificationQuestionAnswerCheckRequest(
    val path: Path,
    val query: Query,
) {
    data class Path(
        val schoolId: String,
    )

    data class Query(
        val answer: String,
    )
}
