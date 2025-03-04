package team.aliens.dms.kmp.core.network.auth.model.request

import kotlinx.serialization.Serializable

data class SignInRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        val accountId: String,
        val password: String,
        val deviceToken: String,
    )
}
