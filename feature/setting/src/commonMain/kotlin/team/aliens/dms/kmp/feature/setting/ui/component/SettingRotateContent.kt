package team.aliens.dms.kmp.feature.setting.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
fun SettingRotateContent(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    rotated: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (rotated) 180f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 0,
            easing = LinearEasing
        ),
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .clickable(
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .rotate(rotationAngle)
                .size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = text,
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.inverseOnSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = if (rotated) "ON" else "OFF",
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseOnSurface,
        )
    }
}
