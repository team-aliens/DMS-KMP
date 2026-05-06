package team.aliens.dms.kmp.core.network.latestudy.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FetchStudyTypesResponse(
    val types: List<StudyTypeResponse>,
)
