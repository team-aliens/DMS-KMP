package team.aliens.dms.kmp.core.designsystem.tab

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = DmsTheme.colors.tertiaryContainer,
    unselectedContentColor: Color = DmsTheme.colors.inverseSurface,
) {
    val (textStyle, textColor) = if (selected) DmsTypography.LBodyB to selectedContentColor else DmsTypography.LBodyM to unselectedContentColor
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = {
            DmsText(
                text = text,
                style = textStyle,
                color = textColor,
            )
        },
        icon = icon,
        interactionSource = interactionSource,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
    )
}
