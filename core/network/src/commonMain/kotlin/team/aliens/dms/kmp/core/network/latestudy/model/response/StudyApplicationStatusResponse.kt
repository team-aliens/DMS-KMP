package team.aliens.dms.kmp.core.network.latestudy.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyApplicationStatusResponse(
    val status: String,
    val dates: List<String>,
    @SerialName("teacher_name")
    val teacherName: String? = null,
    @SerialName("type_name")
    val typeName: String? = null,
    val reason: String? = null,
)
