package team.aliens.dms.kmp.core.network.schools.model.request

data class GetSchoolVerificationCodeCheckRequest (
    val query: Query,
) {
    data class Query(
        val schoolCode: String,
    )
}
