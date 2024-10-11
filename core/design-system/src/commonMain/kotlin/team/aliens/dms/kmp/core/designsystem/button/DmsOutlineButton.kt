package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
private fun BasicOutlineButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    border: BorderStroke,
    shape: Shape,
    backgroundColor: Color,
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    // FIXME: https://youtrack.jetbrains.com/issue/CMP-6668
    /*Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        border = border,
        color = backgroundColor,
        enabled = enabled,
        onClick = onClick,
        content = content,
    )*/

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
            )
            .border(
                border = border,
                shape = shape,
            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
            ),
    ) {
        content()
    }
}

@Composable
fun DmsOutlineButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    destructive: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
    border: BorderStroke = BorderStroke(
        width = 1.dp,
        color = DmsTheme.colors.onSurface,
    ),
    backgroundColor: Color = Color.Transparent,
    onClick: () -> Unit,
) {
    val textColor =
        if (destructive) {
            DmsTheme.colors.outlineVariant
        } else {
            DmsTheme.colors.surfaceContainerLow
        }

    BasicOutlineButton(
        modifier = modifier,
        enabled = enabled,
        border = border,
        shape = shape,
        backgroundColor = backgroundColor,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 14.dp,
                ),
            horizontalArrangement = Arrangement.Center,
        ) {
            DmsText(
                text = text,
                style = DmsTypography.SubtitleSemiBold,
                color = textColor,
            )
        }
    }
}
