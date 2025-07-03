package team.aliens.dms.kmp.core.network.student.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class StudentDto(
    val id: String,
    val name: String,
    val gcn: String,
    val profileImageUrl: String,
)
