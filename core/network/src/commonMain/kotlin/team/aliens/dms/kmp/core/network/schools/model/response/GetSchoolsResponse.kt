package team.aliens.dms.kmp.core.network.schools.model.response

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.network.schools.model.dto.SchoolDto

@Serializable
data class GetSchoolsResponse(
    val schools: List<SchoolDto>,
)
