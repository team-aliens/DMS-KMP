package team.aliens.dms.kmp.core.model.student

data class ResetPasswordModel (
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
)
