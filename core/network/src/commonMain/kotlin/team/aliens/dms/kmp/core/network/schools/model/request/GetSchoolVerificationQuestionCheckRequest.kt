package team.aliens.dms.kmp.core.network.schools.model.request

data class GetSchoolVerificationQuestionCheckRequest(
    val path: Path,
) {
    data class Path(
        val schoolId: String,
    )
}
