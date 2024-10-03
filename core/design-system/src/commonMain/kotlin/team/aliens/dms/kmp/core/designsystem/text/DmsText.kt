package team.aliens.dms.kmp.core.designsystem.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
fun DmsText(
    modifier: Modifier = Modifier,
    color: Color = DmsTheme.colors.surfaceContainerLow,
    text: String,
    style: TextStyle,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    textDecoration: TextDecoration = TextDecoration.None,
) {
    Text(
        modifier = modifier,
        color = color,
        text = text,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign,
        textDecoration = textDecoration,
    )
}
