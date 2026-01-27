package team.aliens.dms.kmp.core.network.user.model

import kotlinx.serialization.Serializable

data class EditPasswordRequest(
    val body: Body,
) {
    @Serializable
    data class Body(
        val currentPassword: String,
        val newPassword: String,
    )
}