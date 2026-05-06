package team.aliens.dms.kmp.core.network.latestudy.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TeacherResponse(
    val id: String,
    val name: String,
)
