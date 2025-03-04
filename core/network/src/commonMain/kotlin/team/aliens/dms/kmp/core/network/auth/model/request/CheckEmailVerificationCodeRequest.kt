package team.aliens.dms.kmp.core.network.auth.model.request

data class CheckEmailVerificationCodeRequest(
    val query: Query,
) {
    data class Query(
        val email: String,
        val authCode: String,
        val type: String,
    )
}
