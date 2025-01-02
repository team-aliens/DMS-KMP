package team.aliens.dms.kmp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.home.viewmodel.HomeState
import team.aliens.dms.kmp.feature.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Home() {
    val viewModel: HomeViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    val (shouldShowCalendar, onShouldShowCalendarChange) = remember { mutableStateOf(false) }

    if (shouldShowCalendar) {
        ModalBottomSheet(
            onDismissRequest = {
                onShouldShowCalendarChange(false)
                // onChangeBottomAppBarVisibility(true)
            },
        ) {
//            DmsCalendar(
//                modifier = Modifier.fillMaxWidth(),
//                selectedDate = uiState.selectedDate,
//                onSelectedDateChange = onSelectedDateChange,
//            )
        }
    }
    HomeScreen(
        state = state,
        onDateChange = viewModel::updateDate,
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onDateChange: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            showLogo = true,
            actions = {
                DmsIconButton(
                    resource = DmsIcon.Notification,
                    tint = DmsTheme.colors.surfaceContainerLow,
                    onClick = { },
                )
            },
        )
        MealCards(
            modifier = Modifier.padding(top = 16.dp),
            onNextDay = { onDateChange(state.selectedDate.plus(DatePeriod(days = 1))) },
            onPreviousDay = { onDateChange(state.selectedDate.minus(DatePeriod(days = 1))) },
            selectDate = state.selectedDate,
        )
    }
}

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    selectDate: LocalDate,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 36.dp,
                vertical = 14.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsIconButton(
            resource = DmsIcon.Backward,
            tint = DmsTheme.colors.surfaceContainerLow,
            size = 18.dp,
            onClick = onPreviousDay,
        )
//        DmsText(
//            modifier = Modifier
//                .clip(RoundedCornerShape(8.dp))
//                .border(
//                    width = 1.dp,
//                    color = DmsTheme.colors.onSurface,
//                    shape = RoundedCornerShape(8.dp),
//                )
//                .clickable(
//                    onClick = { },
//                )
//                .padding(
//                    horizontal = 14.dp,
//                    vertical = 8.dp,
//                ),
//            text = "${selectDate.monthNumber}월 ${selectDate.dayOfMonth}일 ${selectDate.dayOfWeek.text}요일",
//            color = DmsTheme.colors.surfaceContainerLow,
//            style = DmsTypography.Body1SemiBold,
//      )
        DmsIconButton(
            resource = DmsIcon.Forward,
            tint = DmsTheme.colors.surfaceContainerLow,
            size = 18.dp,
            onClick = onNextDay,
        )
    }
}

@Composable
private fun MealCards(
    modifier: Modifier = Modifier,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    selectDate: LocalDate,
) {
    val pageCount = 5
    val pagerState = rememberPagerState(pageCount = { pageCount })
    var previousPage by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage > previousPage) onNextDay()
        if (pagerState.currentPage < previousPage) onPreviousDay()

        previousPage = pagerState.currentPage
    }

    DateCard(
        onNextDay = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        },
        onPreviousDay = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        },
        selectDate = selectDate,
    )

    HorizontalPager(
        modifier = Modifier.padding(top = 24.dp),
        state = pagerState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            val meal: List<String> = listOf("우동국물", "케이준치킨샐러드", "소불고기주먹밥", "배추김치", "해가득사과주스")
            MealCard(
                meal = meal,
            )
            MealCard(
                meal = meal,
            )
            MealCard(
                meal = meal,
            )
        }
    }
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    meal: List<String>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(DmsTheme.colors.onBackground)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val formatMeal = meal.chunked(3).joinToString("\n") { it.joinToString(", ") }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
//            DmsText(
//                text = "아침",
//                style = DmsTypography.HeadlineSemiBold,
//            )
//            DmsText(
//                text = "430Kal",
//                color = DmsTheme.colors.onSurface,
//                style = DmsTypography.Body1Medium,
//            )
        }
//        DmsText(
//            text = formatMeal,
//            style = DmsTypography.Body1Medium,
//        )
    }
}

private val DayOfWeek.text: String
    @Composable inline get() = when (this) {
        DayOfWeek.SUNDAY -> "일"
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        else -> throw IllegalArgumentException()
    }
