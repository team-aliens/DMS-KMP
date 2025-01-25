package team.aliens.dms.kmp.core.designsystem.tag

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsTag(
    modifier: Modifier = Modifier,
    text: String,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
    shape: Shape = RoundedCornerShape(24.dp),
    backgroundColor: Color = DmsTheme.colors.primary,
    contentColor: Color = DmsTheme.colors.inversePrimary,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
    ) {
        DmsText(
            modifier = Modifier.padding(contentPadding),
            text = text,
            style = DmsTypography.Caption,
            color = DmsTheme.colors.inversePrimary,
        )
    }
}
