package team.aliens.dms.kmp.core.network.latestudy.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudyApplicationStatusResponse(
    val status: String,
    @SerialName("start_date")
    val startDate: String? = null,
    @SerialName("end_date")
    val endDate: String? = null,
)
