package team.aliens.dms.kmp.core.network.remains.model.response

import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.Serializable

@Serializable
data class GetRemainsApplicationTimeResponse(
    val startDayOfWeek: DayOfWeek,
    val startTime: String,
    val endDayOfWeek: DayOfWeek,
    val endTime: String,
)
