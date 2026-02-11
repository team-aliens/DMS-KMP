package team.aliens.dms.kmp.core.network.student.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ResetPasswordRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        val accountId: String,
        val name: String,
        val email: String,
        @SerialName("auth_code")
        val emailVerificationCode: String,
        val newPassword: String,
    )
}
