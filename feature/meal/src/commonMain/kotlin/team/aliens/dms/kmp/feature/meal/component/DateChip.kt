package team.aliens.dms.kmp.feature.meal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.common.util.toKorean
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun DateChip(
    modifier: Modifier = Modifier,
    date: LocalDate,
) {
    Row(
        modifier = modifier
            .wrapContentWidth()
            .background(
                color = DmsTheme.colors.background,
                shape = CircleShape,
            )
            .border(width = 1.dp, color = DmsTheme.colors.primary, shape = CircleShape)
            .padding(horizontal = 12.dp, vertical = 16.dp),
    ) {
        DmsText(
            text = "${date.year}.${date.monthNumber}.${date.dayOfMonth} (${date.dayOfWeek.toKorean()})",
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.onPrimaryContainer,
        )
    }
}
