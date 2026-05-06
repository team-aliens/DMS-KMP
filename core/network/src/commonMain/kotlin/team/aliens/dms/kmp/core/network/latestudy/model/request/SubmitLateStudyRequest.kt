package team.aliens.dms.kmp.core.network.latestudy.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitLateStudyRequest(
    @SerialName("teacher_id")
    val teacherId: String,

    @SerialName("type_id")
    val typeId: String,
    val reason: String,

    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,
)
