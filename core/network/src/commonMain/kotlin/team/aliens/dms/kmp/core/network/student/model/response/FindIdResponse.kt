package team.aliens.dms.kmp.core.network.student.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FindIdResponse(
    val email: String,
)
