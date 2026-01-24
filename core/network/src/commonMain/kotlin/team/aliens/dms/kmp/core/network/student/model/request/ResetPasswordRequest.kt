package team.aliens.dms.kmp.core.network.student.model.request

import kotlinx.serialization.SerialName

data class ResetPasswordRequest(
    val body: Body,
) {
    data class Body(
        val accountId: String,
        val name: String,
        val email: String,
        @SerialName("auth_code")
        val emailVerificationCode: String,
        val newPassword: String,
    )
}
