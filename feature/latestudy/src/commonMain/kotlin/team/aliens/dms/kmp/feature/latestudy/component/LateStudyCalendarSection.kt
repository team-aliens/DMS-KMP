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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography

@Composable
fun LateStudyCalendarSection(
    currentMonth: CalendarYearMonth,
    minimumDate: LocalDate,
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
                color = DmsTheme.colors.onBackground,
                style = DmsTypography.BodyB,
            )

            Text(
                text = "(새벽 자습은 금, 토, 일요일은 불가능합니다)",
                color = DmsTheme.colors.inverseSurface,
                style = DmsTypography.BodyM,
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPrevMonthClick) {
                Text(
                    text = "<",
                    color = DmsTheme.colors.onBackground,
                    style = DmsTypography.BodyM,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${currentMonth.year} ${currentMonth.monthNumber}월",
                color = DmsTheme.colors.onBackground,
                style = DmsTypography.BodyB,
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onNextMonthClick) {
                Text(
                    text = ">",
                    color = DmsTheme.colors.onBackground,
                    style = DmsTypography.BodyM,
                )
            }
        }

        CalendarDayHeader()

        CalendarGrid(
            currentMonth = currentMonth,
            minimumDate = minimumDate,
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
                0 -> DmsTheme.colors.error
                5 -> DmsTheme.colors.inverseSurface
                6 -> DmsTheme.colors.primary
                else -> DmsTheme.colors.onBackground
            }

            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = day,
                    color = color,
                    style = DmsTypography.BodyM,
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    currentMonth: CalendarYearMonth,
    minimumDate: LocalDate,
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
                val isSingleSelected = startDate != null &&
                        endDate == null &&
                        item.date == startDate
                val isSelectable = item.isCurrentMonth && isSelectableDate(
                    date = item.date,
                    minimumDate = minimumDate,
                )

                CalendarDateCell(
                    date = item.date,
                    isCurrentMonth = item.isCurrentMonth,
                    isRangeStart = isRangeStart,
                    isRangeEnd = isRangeEnd,
                    isInRange = isInRange,
                    isSingleSelected = isSingleSelected,
                    isSelectable = isSelectable,
                    onClick = {
                        if (isSelectable) {
                            onDateClick(item.date)
                        }
                    },
                    modifier = Modifier.weight(1f),
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
    val textColor = calendarDateTextColor(
        date = date,
        isCurrentMonth = isCurrentMonth,
        isSelected = isSelected,
        isInRange = isInRange,
    )

    Box(
        modifier = modifier.height(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        CalendarRangeBackground(
            isRangeStart = isRangeStart,
            isRangeEnd = isRangeEnd,
            isInRange = isInRange,
            isSingleSelected = isSingleSelected,
        )

        if (isSelected) {
            SelectedDateBackground()
        }

        CalendarDateText(
            date = date,
            textColor = textColor,
            isSelectable = isSelectable,
            onClick = onClick,
        )
    }
}

@Composable
private fun CalendarRangeBackground(
    isRangeStart: Boolean,
    isRangeEnd: Boolean,
    isInRange: Boolean,
    isSingleSelected: Boolean,
) {
    if (isRangeStart && !isSingleSelected) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(start = 15.dp)
                .background(
                    color = DmsTheme.colors.primary.copy(alpha = 0.4f),
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
                .background(DmsTheme.colors.primary.copy(alpha = 0.4f)),
        )
    }

    if (isRangeEnd && !isSingleSelected) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(end = 15.dp)
                .background(
                    color = DmsTheme.colors.primary.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(
                        topEnd = 999.dp,
                        bottomEnd = 999.dp,
                    ),
                ),
        )
    }
}

@Composable
private fun SelectedDateBackground() {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = DmsTheme.colors.primary,
                shape = CircleShape,
            ),
    )
}

@Composable
private fun CalendarDateText(
    date: LocalDate,
    textColor: Color,
    isSelectable: Boolean,
    onClick: () -> Unit,
) {
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
            style = DmsTypography.BodyM,
        )
    }
}

@Composable
private fun calendarDateTextColor(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    isInRange: Boolean,
): Color {
    return when {
        isSelected || isInRange -> DmsTheme.colors.surface
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colors.error.copy(alpha = 0.45f)
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colors.primary.copy(alpha = 0.45f)
        !isCurrentMonth -> DmsTheme.colors.onSurfaceVariant
        date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colors.error
        date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colors.primary
        date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colors.inverseSurface
        else -> DmsTheme.colors.onBackground
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

private fun isSelectableDate(
    date: LocalDate,
    minimumDate: LocalDate,
): Boolean = date >= minimumDate && date.dayOfWeek in setOf(
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
)

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
        return CalendarYearMonth(
            year = newYear,
            monthNumber = newMonth,
        )
    }

    fun plusMonths(months: Int): CalendarYearMonth {
        val totalMonths = year * 12 + (monthNumber - 1) + months
        val newYear = totalMonths.floorDiv(12)
        val newMonth = totalMonths.mod(12) + 1
        return CalendarYearMonth(
            year = newYear,
            monthNumber = newMonth,
        )
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
