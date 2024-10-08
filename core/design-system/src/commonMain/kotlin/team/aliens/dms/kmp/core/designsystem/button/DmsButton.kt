package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Button(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        onClick = onClick,
        color = backgroundColor,
        content = content,
    )
}

@Composable
fun DmsButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (enabled) {
            DmsTheme.colors.onSecondary
        } else {
            DmsTheme.colors.onTertiaryContainer
        },
    )
    Button(
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            ),
        ) {
            DmsText(
                text = text,
                style = DmsTypography.SubtitleSemiBold,
                color = DmsTheme.colors.surfaceContainerHighest,
            )
        }
    }
}

@Composable
fun DmsSmallButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (enabled) {
            DmsTheme.colors.onSecondary
        } else {
            DmsTheme.colors.error
        },
    )
    val contentColor by animateColorAsState(
        targetValue = if (enabled) {
            DmsTheme.colors.surfaceContainerHighest
        } else {
            DmsTheme.colors.surfaceContainerHigh
        },
    )
    Button(
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 8.dp,
            ),
        ) {
            DmsText(
                text = text,
                style = DmsTypography.Body1Medium,
                color = contentColor,
            )
        }
    }
}
