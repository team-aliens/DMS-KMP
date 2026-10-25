package team.aliens.dms.kmp.core.network.latestudy.model.response

import kotlinx.serialization.Serializable

@Serializable
data class StudyApplicationStatusResponse(
    val status: String,
    val dates: List<String>,
)
