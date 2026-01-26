@file:OptIn(ExperimentalTime::class)

package team.aliens.dms.kmp.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

val today: LocalDate
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

val now: LocalDateTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

val timeNow: LocalTime
    inline get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

fun LocalDateTime.toElapsedText(now: LocalDateTime): String {
    val duration = now.toInstant(TimeZone.currentSystemDefault()) - this.toInstant(TimeZone.currentSystemDefault())

    return when {
        duration < 1.minutes -> "${duration.inWholeSeconds}초 전"
        duration < 1.hours -> "${duration.inWholeMinutes}분 전"
        duration < 1.days -> "${duration.inWholeHours}시간 전"
        duration < 30.days -> "${duration.inWholeDays}일 전"
        duration < 365.days -> "${duration.inWholeDays / 30}달 전"
        else -> "${duration.inWholeDays / 365}년 전"
    }
}

fun LocalDate.Companion.now(
    clock: Clock = Clock.System,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDate = clock.todayIn(timeZone)
