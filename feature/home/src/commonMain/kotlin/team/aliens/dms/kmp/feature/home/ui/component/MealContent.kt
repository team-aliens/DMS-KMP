package team.aliens.dms.kmp.feature.home.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.meal.MealModel

@Composable
internal fun MealContent(
    modifier: Modifier = Modifier,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    selectDate: LocalDate,
    meal: MealModel,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DmsText(
            text = "오늘의 급식",
            style = DmsTypography.Title1,
            color = DmsTheme.colors.onTertiaryContainer,
        )
        Spacer(modifier = Modifier.height(60.dp))
        MealCards(
            modifier = Modifier.fillMaxHeight(0.6f),
            onNextDay = onNextDay,
            onPreviousDay = onPreviousDay,
            selectDate = selectDate,
            meal = meal,
        )
    }
}
