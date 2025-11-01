package team.aliens.dms.kmp.feature.meal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun MealContent(
    modifier: Modifier = Modifier,
    daily: String,
    kcal: String?,
    meal: List<String>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f)
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(44.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DmsText(
                text = daily,
                style = DmsTypography.BodyB,
                color = DmsTheme.colors.inverseSurface,
            )
            DmsText(
                text = kcal ?: "",
                style = DmsTypography.BodyM,
                color = DmsTheme.colors.inverseSurface,
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            meal.forEach {
                DmsText(
                    text = it,
                    style = DmsTypography.BodyB,
                    color = DmsTheme.colors.tertiaryContainer,
                )
            }
        }
    }
}
