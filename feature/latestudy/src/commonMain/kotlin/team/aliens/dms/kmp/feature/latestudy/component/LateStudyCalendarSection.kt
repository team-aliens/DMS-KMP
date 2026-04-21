package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import team.aliens.dms.kmp.core.designsystem.DmsTheme

@Composable
fun LateStudyCalendarSection(
    currentMonth: CalendarYearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "일정",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            Text(
                text = "(새벽 자습은 금, 토, 일요일은 불가능합니다)",
                color = DmsTheme.colorScheme.inverseSurface,
                style = DmsTheme.typography.labelM,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "이전 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable(onClick = onPrevMonthClick),
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "${currentMonth.year} ${currentMonth.monthNumber}월",
                color = DmsTheme.colorScheme.onBackground,
                style = DmsTheme.typography.bodyB,
            )

            Spacer(modifier = Modifier.size(4.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "다음 달",
                tint = DmsTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.clickable(onClick = onNextMonthClick),
            )
        }

        CalendarDayHeader()

        CalendarGrid(
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onDateClick = onDateClick,
        )

        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
private fun CalendarDayHeader() {
    val days = listOf("일", "월", "화", "수", "목", "금", "토")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        days.forEachIndexed { index, day ->
            val color = when (index) {
                0 -> DmsTheme.colorScheme.errorContainer
                6 -> DmsTheme.colorScheme.onPrimary
                5 -> DmsTheme.colorScheme.onSurfaceVariant
                else -> DmsTheme.colorScheme.onBackground
            }

            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = day,
                    color = color,
                    style = DmsTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun CalendarDateCell(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isRangeStart: Boolean,
    isRangeEnd: Boolean,
    isInRange: Boolean,
    isSingleSelected: Boolean,
    isSelectable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isSelected = isRangeStart || isRangeEnd

    val textColor = when {
        isSelected || isInRange -> DmsTheme.colorScheme.background
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.onError
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        !isCurrentMonth -> DmsTheme.colorScheme.inverseSurface
        date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colorScheme.onError
        date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colorScheme.onPrimary
        date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colorScheme.onSurfaceVariant
        else -> DmsTheme.colorScheme.onBackground
    }

    Box(
        modifier = modifier.height(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        if (isRangeStart && !isSingleSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(start = 15.dp)
                    .background(
                        color = DmsTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(
                            topStart = 999.dp,
                            bottomStart = 999.dp,
                        ),
                    ),
            )
        }

        if (isInRange) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(
                        color = DmsTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                    ),
            )
        }

        if (isRangeEnd && !isSingleSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(end = 15.dp)
                    .background(
                        color = DmsTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(
                            topEnd = 999.dp,
                            bottomEnd = 999.dp,
                        ),
                    ),
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = DmsTheme.colorScheme.onPrimaryContainer,
                        shape = CircleShape,
                    ),
            )
        }

        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    enabled = isSelectable,
                    onClick = onClick,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                color = textColor,
                style = DmsTheme.typography.caption,
            )
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: CalendarYearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateClick: (LocalDate) -> Unit,
) {
    val dates = buildCalendarDates(currentMonth)

    dates.chunked(7).forEach { week ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
        ) {
            week.forEach { item ->
                val isRangeStart = item.date == startDate
                val isRangeEnd = item.date == endDate
                val isInRange = startDate != null &&
                        endDate != null &&
                        item.date > startDate &&
                        item.date < endDate
                val isSingleSelected = startDate != null && endDate == null && item.date == startDate
                val isSelectable = item.isCurrentMonth && isSelectableDate(item.date)

                CalendarDateCell(
                    date = item.date,
                    isCurrentMonth = item.isCurrentMonth,
                    isRangeStart = isRangeStart,
                    isRangeEnd = isRangeEnd,
                    isInRange = isInRange,
                    isSingleSelected = isSingleSelected,
                    isSelectable = isSelectable,
                    onClick = {
                        if (isSelectable) onDateClick(item.date)
                    },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

private data class CalendarDateUiModel(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
)

private fun buildCalendarDates(
    currentMonth: CalendarYearMonth,
): List<CalendarDateUiModel> {
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOffset = firstDayOfMonth.dayOfWeek.isoDayNumber % 7
    val daysInMonth = currentMonth.lengthOfMonth()

    val previousMonth = currentMonth.minusMonths(1)
    val previousMonthLastDay = previousMonth.lengthOfMonth()

    val result = mutableListOf<CalendarDateUiModel>()

    repeat(firstDayOffset) { index ->
        val day = previousMonthLastDay - firstDayOffset + index + 1
        result.add(
            CalendarDateUiModel(
                date = previousMonth.atDay(day),
                isCurrentMonth = false,
            ),
        )
    }

    for (day in 1..daysInMonth) {
        result.add(
            CalendarDateUiModel(
                date = currentMonth.atDay(day),
                isCurrentMonth = true,
            ),
        )
    }

    var nextMonthDay = 1
    while (result.size % 7 != 0) {
        result.add(
            CalendarDateUiModel(
                date = currentMonth.plusMonths(1).atDay(nextMonthDay),
                isCurrentMonth = false,
            ),
        )
        nextMonthDay++
    }

    return result
}

private fun isSelectableDate(date: LocalDate): Boolean {
    return when (date.dayOfWeek) {
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
            -> false

        else -> true
    }
}

data class CalendarYearMonth(
    val year: Int,
    val monthNumber: Int,
) {
    init {
        require(monthNumber in 1..12)
    }

    fun atDay(day: Int): LocalDate = LocalDate(
        year = year,
        monthNumber = monthNumber,
        dayOfMonth = day,
    )

    fun lengthOfMonth(): Int = when (monthNumber) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> error("Invalid month")
    }

    fun minusMonths(months: Int): CalendarYearMonth {
        val totalMonths = year * 12 + (monthNumber - 1) - months
        val newYear = totalMonths.floorDiv(12)
        val newMonth = totalMonths.mod(12) + 1
        return CalendarYearMonth(newYear, newMonth)
    }

    fun plusMonths(months: Int): CalendarYearMonth {
        val totalMonths = year * 12 + (monthNumber - 1) + months
        val newYear = totalMonths.floorDiv(12)
        val newMonth = totalMonths.mod(12) + 1
        return CalendarYearMonth(newYear, newMonth)
    }
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

private val DayOfWeek.isoDayNumber: Int
    get() = when (this) {
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
        DayOfWeek.SUNDAY -> 7
    }
