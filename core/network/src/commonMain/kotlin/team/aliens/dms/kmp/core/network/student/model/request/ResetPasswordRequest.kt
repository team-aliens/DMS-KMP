package team.aliens.dms.kmp.core.network.student.model.request

data class ResetPasswordRequest(
    val body: Body,
) {
    data class Body(
        val accountId: String,
        val studentName: String,
        val email: String,
        val emailVerificationCode: String,
        val newPassword: String,
    )
}
