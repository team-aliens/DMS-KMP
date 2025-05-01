package team.aliens.dms.kmp.feature.mypage.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.type.PointType

@Composable
internal fun PointCards(
    modifier: Modifier = Modifier,
    bonusPoint: Int,
    minusPoint: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        PointCard(
            modifier = Modifier.weight(1f),
            point = bonusPoint,
            pointType = PointType.BONUS,
        )
        PointCard(
            modifier = Modifier.weight(1f),
            point = minusPoint,
            pointType = PointType.MINUS,
        )
    }
}

@Composable
private fun PointCard(
    modifier: Modifier = Modifier,
    point: Int,
    pointType: PointType,
) {
    val (text, textColor, backgroundColor) = when (pointType) {
        PointType.BONUS -> Triple("상점", DmsTheme.colors.secondary, DmsTheme.colors.primary)
        PointType.MINUS -> Triple("벌점", DmsTheme.colors.onErrorContainer, DmsTheme.colors.error)
    }
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = backgroundColor,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = textColor,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DmsText(
                text = text,
                style = DmsTypography.Label,
                color = textColor,
            )
            DmsText(
                modifier = Modifier.fillMaxWidth(),
                text = point.toString(),
                style = DmsTypography.Header2,
                color = textColor,
                textAlign = TextAlign.Right,
            )
        }
    }
}
