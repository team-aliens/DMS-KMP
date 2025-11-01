package team.aliens.dms.kmp.core.designsystem.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.minusYears
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusYears
import kotlinx.datetime.LocalDate
import kotlinx.datetime.YearMonth
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun DmsCalendar(
    modifier: Modifier = Modifier,
    selectDate: LocalDate,
    onSelectedDateChange: (newDate: LocalDate) -> Unit,
) {
    var updateDate by remember { mutableStateOf(selectDate) }
    val calendarState = rememberCalendarState(
        startMonth = YearMonth.now().minusYears(1),
        endMonth = YearMonth.now().plusYears(1),
        firstVisibleMonth = YearMonth.now(),
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DmsTheme.colors.surfaceTint)
            .navigationBarsPadding(),
    ) {
        HorizontalCalendar(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            state = calendarState,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = day.date == updateDate,
                    onDayClick = { updateDate = it },
                )
            },
        )

        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .verticalPadding(12.dp),
            text = "확인",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = { onSelectedDateChange(updateDate) },
        )
    }
}

@Composable
private fun Day(
    modifier: Modifier = Modifier,
    day: CalendarDay,
    isSelected: Boolean,
    onDayClick: (LocalDate) -> Unit,
) {
    val isOtherMonthDay = day.position != DayPosition.MonthDate
    val textColor = when {
        isSelected -> DmsTheme.colors.surface
        isOtherMonthDay -> DmsTheme.colors.onSurfaceVariant
        else -> DmsTheme.colors.inverseSurface
    }
    val backgroundColor = when {
        isSelected -> DmsTheme.colors.onPrimaryContainer
        else -> Color.Transparent
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onDayClick(day.date) })
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(
                    color = backgroundColor,
                    shape = CircleShape,
                ),
            contentAlignment = Alignment.Center,
        ) {
            DmsText(
                text = day.date.day.toString(),
                style = DmsTypography.BodyM,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}
