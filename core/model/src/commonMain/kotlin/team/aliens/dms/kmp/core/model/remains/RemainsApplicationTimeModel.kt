package team.aliens.dms.kmp.core.model.remains

import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.Serializable

@Serializable
data class RemainsApplicationTimeModel(
    val startDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val startTime: String = "",
    val endDayOfWeek: DayOfWeek = DayOfWeek.SUNDAY,
    val endTime: String = "",
)
