package team.aliens.dms.kmp.core.util

import kotlinx.datetime.LocalDateTime

fun LocalDateTime.toDateString(): String {
    return "${this.year}년 ${this.monthNumber}월 ${this.dayOfMonth}일"
}
