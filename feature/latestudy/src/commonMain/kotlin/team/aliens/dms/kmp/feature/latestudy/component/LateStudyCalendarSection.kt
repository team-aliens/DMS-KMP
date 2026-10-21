package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography

@Composable
fun LateStudyCalendarSection(
    currentMonth: CalendarYearMonth,
    minimumDate: LocalDate,
    selectedDates: Set<LocalDate>,
    onPrevMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onDateRangeSelect: (Set<LocalDate>) -> Unit,
    onPastDateClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "일정",
                color = DmsTheme.colors.inverseOnSurface,
                style = DmsTypography.BodyB,
            )

            Text(
                text = "(금, 토, 일요일은 불가능 · 길게 눌러 드래그해 여러 날짜 선택)",
                color = DmsTheme.colors.inverseSurface,
                style = DmsTypography.labelM,
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onPrevMonthClick) {
                Text(
                    text = "<",
                    color = DmsTheme.colors.scrim,
                    style = DmsTypography.BodyM,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "${currentMonth.year} ${currentMonth.monthNumber}월",
                color = DmsTheme.colors.tertiaryContainer,
                style = DmsTypography.BodyM,
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = onNextMonthClick) {
                Text(
                    text = ">",
                    color = DmsTheme.colors.scrim,
                    style = DmsTypography.BodyM,
                )
            }
        }

        CalendarDayHeader()

        CalendarGrid(
            currentMonth = currentMonth,
            minimumDate = minimumDate,
            selectedDates = selectedDates,
            onDateClick = onDateClick,
            onDateRangeSelect = onDateRangeSelect,
            onPastDateClick = onPastDateClick,
        )

        Spacer(modifier = Modifier.size(24.dp))
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
                0 -> DmsTheme.colors.onError
                5 -> DmsTheme.colors.onSurfaceVariant
                6 -> DmsTheme.colors.onPrimary
                else -> DmsTheme.colors.tertiaryContainer
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
    selectedDates: Set<LocalDate>,
    onDateClick: (LocalDate) -> Unit,
    onDateRangeSelect: (Set<LocalDate>) -> Unit,
    onPastDateClick: () -> Unit,
) {
    val dates = buildCalendarDates(currentMonth)
    val density = LocalDensity.current
    val horizontalPadding = with(density) { 16.dp.toPx() }
    val rowHeight = with(density) { 52.dp.toPx() }
    val haptic = LocalHapticFeedback.current
    var rangeStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var rangeEndDate by remember { mutableStateOf<LocalDate?>(null) }
    val visibleSelectedDates = selectedDates + selectableDatesBetween(
        firstDate = rangeStartDate,
        secondDate = rangeEndDate,
        minimumDate = minimumDate,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(dates, minimumDate) {
                detectDragGesturesAfterLongPress(
                    onDragStart = { position ->
                        rangeStartDate = calendarDateAtPosition(
                            position = position,
                            dates = dates,
                            gridWidth = size.width.toFloat(),
                            horizontalPadding = horizontalPadding,
                            rowHeight = rowHeight,
                        )?.takeIf { isSelectableDate(it, minimumDate) }
                        rangeEndDate = rangeStartDate
                        if (rangeStartDate != null) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                    },
                    onDrag = { change, _ ->
                        rangeEndDate = calendarDateAtPosition(
                            position = change.position,
                            dates = dates,
                            gridWidth = size.width.toFloat(),
                            horizontalPadding = horizontalPadding,
                            rowHeight = rowHeight,
                        )?.takeIf { isSelectableDate(it, minimumDate) } ?: rangeEndDate
                    },
                    onDragEnd = {
                        onDateRangeSelect(
                            selectableDatesBetween(
                                firstDate = rangeStartDate,
                                secondDate = rangeEndDate,
                                minimumDate = minimumDate,
                            ),
                        )
                        rangeStartDate = null
                        rangeEndDate = null
                    },
                    onDragCancel = {
                        rangeStartDate = null
                        rangeEndDate = null
                    },
                )
            },
    ) {
        dates.chunked(7).forEach { week ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp),
            ) {
                week.forEachIndexed { index, item ->
                    val isSelected = item.date in visibleSelectedDates
                    val isConnectedToPrevious = isSelected &&
                        index > 0 &&
                        week[index - 1].date in visibleSelectedDates
                    val isConnectedToNext = isSelected &&
                        index < week.lastIndex &&
                        week[index + 1].date in visibleSelectedDates
                    val isSelectable = isSelectableDate(
                        date = item.date,
                        minimumDate = minimumDate,
                    )
                    val isPastDate = item.date < minimumDate

                    CalendarDateCell(
                        date = item.date,
                        isCurrentMonth = item.isCurrentMonth,
                        isSelected = isSelected,
                        isConnectedToPrevious = isConnectedToPrevious,
                        isConnectedToNext = isConnectedToNext,
                        onClick = {
                            if (isSelectable) {
                                onDateClick(item.date)
                            } else if (isPastDate) {
                                onPastDateClick()
                            }
                        },
                        isClickable = isSelectable || isPastDate,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarDateCell(
    date: LocalDate,
    isCurrentMonth: Boolean,
    isSelected: Boolean,
    isConnectedToPrevious: Boolean,
    isConnectedToNext: Boolean,
    isClickable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = calendarDateTextColor(
        date = date,
        isCurrentMonth = isCurrentMonth,
        isSelected = isSelected,
    )

    Box(
        modifier = modifier.height(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        ConnectedDateBackground(
            isConnectedToPrevious = isConnectedToPrevious,
            isConnectedToNext = isConnectedToNext,
        )

        SelectedDateBackground(isSelected = isSelected)

        CalendarDateText(
            date = date,
            textColor = textColor,
            isSelectable = isClickable,
            onClick = onClick,
        )
    }
}

@Composable
private fun ConnectedDateBackground(
    isConnectedToPrevious: Boolean,
    isConnectedToNext: Boolean,
) {
    val previousColor by animateColorAsState(
        targetValue = if (isConnectedToPrevious) {
            DmsTheme.colors.primaryContainer
        } else {
            Color.Transparent
        },
        label = "previous selection connector",
    )
    val nextColor by animateColorAsState(
        targetValue = if (isConnectedToNext) {
            DmsTheme.colors.primaryContainer
        } else {
            Color.Transparent
        },
        label = "next selection connector",
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(previousColor),
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
                .background(nextColor),
        )
    }
}

@Composable
private fun SelectedDateBackground(
    isSelected: Boolean,
) {
    val size by animateDpAsState(
        targetValue = if (isSelected) 28.dp else 0.dp,
        label = "selected date size",
    )
    val color by animateColorAsState(
        targetValue = if (isSelected) {
            DmsTheme.colors.onPrimaryContainer
        } else {
            Color.Transparent
        },
        label = "selected date color",
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(
                color = color,
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
): Color {
    return when {
        isSelected -> Color.White
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colors.onError
        !isCurrentMonth && date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colors.onPrimary
        !isCurrentMonth -> DmsTheme.colors.inverseSurface
        date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colors.onError
        date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colors.onPrimary
        date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colors.onSurfaceVariant
        else -> DmsTheme.colors.tertiaryContainer
    }
}

private fun calendarDateAtPosition(
    position: Offset,
    dates: List<CalendarDateUiModel>,
    gridWidth: Float,
    horizontalPadding: Float,
    rowHeight: Float,
): LocalDate? {
    val contentWidth = gridWidth - (horizontalPadding * 2)
    if (contentWidth <= 0f || position.x !in horizontalPadding..(gridWidth - horizontalPadding)) {
        return null
    }

    val column = ((position.x - horizontalPadding) / (contentWidth / 7)).toInt()
    val row = (position.y / rowHeight).toInt()
    return dates.getOrNull(row * 7 + column)?.date
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

private fun selectableDatesBetween(
    firstDate: LocalDate?,
    secondDate: LocalDate?,
    minimumDate: LocalDate,
): Set<LocalDate> {
    if (firstDate == null || secondDate == null) return emptySet()

    val startDate = minOf(firstDate, secondDate)
    val endDate = maxOf(firstDate, secondDate)
    val selectedDates = mutableSetOf<LocalDate>()
    var date = startDate

    while (date <= endDate) {
        if (isSelectableDate(date, minimumDate)) {
            selectedDates += date
        }
        date = date.plus(1, DateTimeUnit.DAY)
    }

    return selectedDates
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
