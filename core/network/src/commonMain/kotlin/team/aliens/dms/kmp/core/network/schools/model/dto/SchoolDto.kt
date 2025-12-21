package team.aliens.dms.kmp.core.network.schools.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class SchoolDto(
    val id: String,
    val name: String,
    val address: String,
)
