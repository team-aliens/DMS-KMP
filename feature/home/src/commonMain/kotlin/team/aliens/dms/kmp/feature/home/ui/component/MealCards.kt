package team.aliens.dms.kmp.feature.home.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.ic_dinner
import dmskmp.core.design_system.generated.resources.ic_launch
import dmskmp.core.design_system.generated.resources.ic_morning
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.common.utils.now
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.meal.MealModel
import kotlin.math.absoluteValue

@Composable
internal fun MealCards(
    modifier: Modifier = Modifier,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    selectDate: LocalDate,
    meal: MealModel,
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        pageCount = { pageCount },
    )
    var previousPage by remember { mutableStateOf(pagerState.currentPage) }
    val scope = rememberCoroutineScope()
    var currentCardType by remember { mutableStateOf(getProperMeal()) }
    val (dailyMeals, kcal) = when (currentCardType) {
        MealCardType.BREAKFAST -> meal.breakfast to meal.kcalBreakfast
        MealCardType.LUNCH -> meal.lunch to meal.kcalLunch
        MealCardType.DINNER -> meal.dinner to meal.kcalDinner
    }

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
        modifier = modifier.padding(top = 24.dp),
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 84.dp),
        pageSpacing = 30.dp,
        beyondViewportPageCount = 1,
    ) { page ->
        MealCard(
            modifier = Modifier.graphicsLayer {
                val pagerOffset =
                    ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                lerp(
                    start = 0.85f,
                    stop = 1f,
                    fraction = 1f - pagerOffset.coerceIn(
                        minimumValue = 0f,
                        maximumValue = 1f,
                    ),
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                alpha = lerp(
                    start = 0.4f,
                    stop = 1f,
                    fraction = 1f - pagerOffset.coerceIn(
                        minimumValue = 0f,
                        maximumValue = 1f,
                    ),
                )
            },
            meal = dailyMeals,
            kcal = kcal,
            mealCardType = currentCardType,
        )
    }
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    meal: List<String>,
    kcal: String?,
    mealCardType: MealCardType,
) {
    val formatMeal = meal.joinToString("\n")
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DmsTheme.colors.background,
                shape = RoundedCornerShape(20.dp),
            )
            .border(
                width = 1.dp,
                color = DmsTheme.colors.inversePrimary,
                shape = RoundedCornerShape(20.dp),
            )
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.spacedBy(54.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(mealCardType.iconRes),
            contentDescription = null,
        )
        DmsText(
            text = formatMeal,
            style = DmsTypography.Body2,
            color = DmsTheme.colors.onSecondary,
            textAlign = TextAlign.Center,
        )
        kcal?.let {
            DmsText(
                text = it,
                style = DmsTypography.Body2,
                color = DmsTheme.colors.inverseSurface,
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

internal enum class MealCardType(
    val iconRes: DrawableResource,
) {
    BREAKFAST(
        iconRes = Res.drawable.ic_morning,
    ),
    LUNCH(
        iconRes = Res.drawable.ic_launch,
    ),
    DINNER(
        iconRes = Res.drawable.ic_dinner,
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
