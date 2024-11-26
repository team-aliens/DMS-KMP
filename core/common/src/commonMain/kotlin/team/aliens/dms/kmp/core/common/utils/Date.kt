package team.aliens.dms.kmp.core.common.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val today: LocalDate
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

val now: LocalDateTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.UTC)

val timeNow: LocalTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
