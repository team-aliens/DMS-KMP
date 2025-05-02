package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordResponse(
    val accountId: String,
    val name: String,
    val email: String,
    val authCode: String,
    val newPassword: String,
)
