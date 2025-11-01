@file:OptIn(ExperimentalTime::class)

package team.aliens.dms.kmp.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

val today: LocalDate
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

val now: LocalDateTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

val timeNow: LocalTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

fun LocalDate.Companion.now(
    clock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate = clock.todayIn(timeZone)
