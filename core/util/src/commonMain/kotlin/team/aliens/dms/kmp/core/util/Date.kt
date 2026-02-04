@file:OptIn(ExperimentalTime::class)

package team.aliens.dms.kmp.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

val today: LocalDate
    inline get() = Clock.System.todayIn(TimeZone.currentSystemDefault())

val now: LocalDateTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

val timeNow: LocalTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

fun LocalDateTime.toElapsedText(now: LocalDateTime): String {
    val startInstant = this.toInstant(TimeZone.currentSystemDefault())
    val endInstant = now.toInstant(TimeZone.currentSystemDefault())
    val period = startInstant.periodUntil(endInstant, TimeZone.currentSystemDefault())

    return when {
        period.years > 0 -> "${period.years}년 전"
        period.months > 0 -> "${period.months}달 전"
        period.days > 0 -> "${period.days}일 전"
        period.hours > 0 -> "${period.hours}시간 전"
        period.minutes > 0 -> "${period.minutes}분 전"
        else -> "${period.seconds}초 전"
    }
}

fun LocalDate.Companion.now(
    clock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate = clock.todayIn(timeZone)
