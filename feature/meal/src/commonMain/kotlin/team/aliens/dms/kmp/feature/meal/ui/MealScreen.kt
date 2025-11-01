package team.aliens.dms.kmp.feature.meal.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_calendar
import dmskmp.core.design_system.generated.resources.img_dinner
import dmskmp.core.design_system.generated.resources.img_launch
import dmskmp.core.design_system.generated.resources.img_morning
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.endPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.button.DmsItemButton
import team.aliens.dms.kmp.core.designsystem.calendar.DmsCalendar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.util.now
import team.aliens.dms.kmp.feature.meal.component.DateChip
import team.aliens.dms.kmp.feature.meal.component.MealContent
import team.aliens.dms.kmp.feature.meal.viewmodel.MealState
import team.aliens.dms.kmp.feature.meal.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MealScreen(
    onNavigateBack: () -> Unit,
) {
    val viewModel: MealViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val calendarBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (state.isShowCalendar) {
        ModalBottomSheet(
            sheetState = calendarBottomSheetState,
            containerColor = DmsTheme.colors.surfaceTint,
            onDismissRequest = viewModel::hideCalendarBottomSheet,
        ) {
            DmsCalendar(
                modifier = Modifier.fillMaxWidth(),
                selectDate = state.selectedDate,
                onSelectedDateChange = viewModel::setDate,
            )
        }
    }
    MealScreen(
        state = state,
        onNextDay = viewModel::setNextDate,
        onPreviousDay = viewModel::setPreviousDate,
        onBackClick = onNavigateBack,
        onCalendarClick = viewModel::showCalendarBottomSheet,
    )
}

@Composable
private fun MealScreen(
    state: MealState,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit,
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount },
    )
    val meal = state.meal
    var previousPage by remember { mutableStateOf(pagerState.currentPage) }
    val scope = rememberCoroutineScope()
    var currentCardType by remember { mutableStateOf(getProperMeal()) }
    val (dailyMeals, kcal) = when (currentCardType) {
        MealCardType.BREAKFAST -> meal.breakfast to meal.kcalBreakfast
        MealCardType.LUNCH -> meal.lunch to meal.kcalLunch
        MealCardType.DINNER -> meal.dinner to meal.kcalDinner
    }
    val mealCardGradientColors = when (currentCardType) {
        MealCardType.BREAKFAST -> listOf(Color(0xFF0F6EFE), Color(0xFFFFCB52))
        MealCardType.LUNCH -> listOf(Color(0xFF0F6EFE), Color(0xFFFFFFFF))
        MealCardType.DINNER -> listOf(Color(0xFF7A3BA1), Color(0xFFFFFFFF))
    }
    val backgroundGradient = Brush.verticalGradient(mealCardGradientColors)

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage > previousPage) {
            currentCardType = when (currentCardType) {
                MealCardType.BREAKFAST -> MealCardType.LUNCH
                MealCardType.LUNCH -> MealCardType.DINNER
                MealCardType.DINNER -> MealCardType.BREAKFAST.also { onNextDay() }
            }
        }
        if (pagerState.currentPage < previousPage) {
            currentCardType = when (currentCardType) {
                MealCardType.BREAKFAST -> MealCardType.DINNER.also { onPreviousDay() }
                MealCardType.LUNCH -> MealCardType.BREAKFAST
                MealCardType.DINNER -> MealCardType.LUNCH
            }
        }

        previousPage = pagerState.currentPage
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackClick,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Canvas(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .topPadding(70.dp)
                    .size(300.dp)
                    .blur(radius = 120.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
                onDraw = {
                    drawCircle(backgroundGradient)
                },
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                DateChip(date = state.selectedDate)

                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = pagerState,
                    ) { page ->
                        val mealPage = page + getProperMeal().ordinal
                        val cardType = when (mealPage % 3) {
                            0 -> MealCardType.BREAKFAST
                            1 -> MealCardType.LUNCH
                            else -> MealCardType.DINNER
                        }
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(200.dp)
                                .align(Alignment.Center),
                            painter = painterResource(cardType.iconRes),
                            contentDescription = null,
                        )
                    }
                    DmsIconButton(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .startPadding(24.dp),
                        resource = DmsIcon.Backward,
                        tint = DmsTheme.colors.scrim,
                        size = 34.dp,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    )
                    DmsIconButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .endPadding(24.dp)
                            .rotate(180f),
                        resource = DmsIcon.Backward,
                        tint = DmsTheme.colors.scrim,
                        size = 34.dp,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        },
                    )
                }
                MealContent(
                    modifier = Modifier
                        .topPadding(20.dp)
                        .horizontalPadding(10.dp),
                    daily = currentCardType.title,
                    kcal = kcal,
                    meal = dailyMeals,
                )
                DmsItemButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .topPadding(20.dp)
                        .horizontalPadding(10.dp),
                    iconRes = Res.drawable.img_calendar,
                    text = "급식 캘린더 보기",
                    onClick = onCalendarClick,
                )
            }
        }
    }
}

internal enum class MealCardType(
    val title: String,
    val iconRes: DrawableResource,
) {
    BREAKFAST(
        title = "아침",
        iconRes = Res.drawable.img_morning,
    ),
    LUNCH(
        title = "점심",
        iconRes = Res.drawable.img_launch,
    ),
    DINNER(
        title = "저녁",
        iconRes = Res.drawable.img_dinner,
    ),
}

private const val BreakfastStartTime: Int = 9
private const val LunchStartTime: Int = 13
private const val DinnerStartTime: Int = 19

private fun getProperMeal(): MealCardType = when (now.hour) {
    in BreakfastStartTime until LunchStartTime -> MealCardType.LUNCH
    in LunchStartTime until DinnerStartTime -> MealCardType.DINNER
    else -> MealCardType.BREAKFAST
}
