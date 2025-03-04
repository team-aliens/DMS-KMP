package team.aliens.dms.kmp.core.network.auth.model.request

import kotlinx.serialization.Serializable

data class SendEmailVerificationCodeRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        val email: String,
        val type: String,
    )
}
