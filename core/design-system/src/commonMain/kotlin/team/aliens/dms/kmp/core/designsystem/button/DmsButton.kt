package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    // FIXME: https://youtrack.jetbrains.com/issue/CMP-6668
    /*    Surface(
            modifier = modifier,
            shape = RoundedCornerShape(8.dp),
            enabled = enabled,
            onClick = onClick,
            color = backgroundColor,
            content = content,
        )*/

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = enabled,
            ),
    ) {
        content()
    }
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
    BasicButton(
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp,
                ),
            horizontalArrangement = Arrangement.Center,
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
    BasicButton(
        modifier = modifier,
        enabled = enabled,
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 8.dp,
                ),
            horizontalArrangement = Arrangement.Center,
        ) {
            DmsText(
                text = text,
                style = DmsTypography.Body1Medium,
                color = contentColor,
            )
        }
    }
}
