package team.aliens.dms.kmp.feature.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.ic_dinner
import dmskmp.core.design_system.generated.resources.ic_launch
import dmskmp.core.design_system.generated.resources.ic_morning
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.meal.MealModel
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
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
        MealCards(
            modifier = Modifier.padding(top = 16.dp),
            onNextDay = { onDateChange(state.selectedDate.plus(DatePeriod(days = 1))) },
            onPreviousDay = { onDateChange(state.selectedDate.minus(DatePeriod(days = 1))) },
            selectDate = state.selectedDate,
            meal = state.meal,
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
    meal: MealModel,
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

//    DateCard(
//        modifier = modifier.fillMaxWidth(),
//        onNextDay = {
//            scope.launch {
//                pagerState.animateScrollToPage(pagerState.currentPage + 1)
//            }
//        },
//        onPreviousDay = {
//            scope.launch {
//                pagerState.animateScrollToPage(pagerState.currentPage - 1)
//            }
//        },
//        selectDate = selectDate,
//    )

    HorizontalPager(
        modifier = Modifier.padding(top = 24.dp),
        state = pagerState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp),
        ) {
            MealCard(
                meal = meal.breakfast,
                kcal = meal.kcalBreakfast,
                mealCardType = MealCardType.BREAKFAST,
            )
            MealCard(
                meal = meal.lunch,
                kcal = meal.kcalLunch,
                mealCardType = MealCardType.LUNCH,
            )
            MealCard(
                meal = meal.dinner,
                kcal = meal.kcalDinner,
                mealCardType = MealCardType.DINNER,
            )
        }
    }
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    meal: List<String>,
    kcal: String?,
    mealCardType: MealCardType,
) {
    val formatMeal = meal.chunked(1).joinToString("\n") { it.joinToString(", ") }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DmsTheme.colors.background)
            .padding(
                horizontal = 26.dp,
                vertical = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = DmsTheme.colors.primary,
                    )
                    .padding(8.dp),
                painter = painterResource(mealCardType.iconRes),
                contentDescription = null,
            )
            DmsText(
                text = mealCardType.title,
                style = DmsTypography.Header3,
                color = DmsTheme.colors.surfaceBright,
            )
        }
        DmsText(
            text = formatMeal,
            style = DmsTypography.Body2,
            color = DmsTheme.colors.onSecondary,
        )
        kcal?.let {
            DmsText(
                modifier = Modifier
                    .background(
                        shape = CircleShape,
                        color = DmsTheme.colors.primary,
                    )
                    .padding(12.dp),
                text = it,
                style = DmsTypography.Body3,
                color = DmsTheme.colors.onTertiaryContainer,
            )
        }
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

private enum class MealCardType(
    val iconRes: DrawableResource,
    val title: String,
) {
    BREAKFAST(
        iconRes = Res.drawable.ic_morning,
        title = "아침",
    ),
    LUNCH(
        iconRes = Res.drawable.ic_launch,
        title = "점심",
    ),
    DINNER(
        iconRes = Res.drawable.ic_dinner,
        title = "저녁",
    )
}
