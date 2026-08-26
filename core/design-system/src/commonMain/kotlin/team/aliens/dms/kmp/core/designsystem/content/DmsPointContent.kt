package team.aliens.dms.kmp.core.designsystem.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsPointContent(
    modifier: Modifier = Modifier,
    plusPoint: Int,
    minusPoint: Int,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(
                    color = DmsTheme.colors.surfaceTint,
                    shape = RoundedCornerShape(32.dp),
                )
                .padding(24.dp),
    ) {
        DmsText(
            text = "상벌점",
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.inverseSurface,
        )
        PointItem(
            modifier = Modifier.padding(top = 16.dp),
            iconBackgroundColor = DmsTheme.colors.onSurfaceVariant,
            textColor = DmsTheme.colors.onTertiaryContainer,
            buttonColor = DmsTheme.colors.onSurface,
            icon = DmsIcon.Equal,
            title = "총점",
            point = plusPoint - minusPoint,
        )
        PointItem(
            modifier = Modifier.padding(top = 12.dp),
            iconBackgroundColor = DmsTheme.colors.onPrimary,
            textColor = DmsTheme.colors.onPrimaryContainer,
            buttonColor = DmsTheme.colors.primary,
            icon = DmsIcon.Plus,
            title = "상점",
            point = plusPoint,
        )
        PointItem(
            modifier = Modifier.padding(top = 12.dp),
            iconBackgroundColor = DmsTheme.colors.onError,
            textColor = DmsTheme.colors.onErrorContainer,
            buttonColor = DmsTheme.colors.error,
            icon = DmsIcon.Minus,
            title = "벌점",
            point = minusPoint,
        )
    }
}

@Composable
private fun PointItem(
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color,
    textColor: Color,
    buttonColor: Color,
    icon: DrawableResource,
    title: String,
    point: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor)
                    .padding(6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(icon),
                tint = textColor,
                contentDescription = null,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        DmsText(
            text = title,
            style = DmsTypography.BodyB,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsText(
            modifier =
                Modifier
                    .background(color = buttonColor, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 22.dp, vertical = 8.dp),
            text = "${point}점",
            style = DmsTypography.BodyB,
            color = textColor,
        )
    }
}
