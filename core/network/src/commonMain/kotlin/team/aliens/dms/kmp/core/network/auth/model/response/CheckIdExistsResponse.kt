package team.aliens.dms.kmp.core.network.auth.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckIdExistsResponse(
    val email: String,
)
