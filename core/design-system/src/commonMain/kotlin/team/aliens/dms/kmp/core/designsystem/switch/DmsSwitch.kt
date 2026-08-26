package team.aliens.dms.kmp.core.designsystem.switch

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
fun DmsSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    thumbContent: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: SwitchColors = DmsSwitchDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) = Switch(
    checked = checked,
    onCheckedChange = onCheckedChange,
    modifier = modifier,
    thumbContent = thumbContent,
    enabled = enabled,
    colors = colors,
    interactionSource = interactionSource,
)

object DmsSwitchDefaults {
    @Composable
    fun colors(
        checkedThumbColor: Color = DmsTheme.colors.surfaceTint,
        checkedTrackColor: Color = DmsTheme.colors.secondary,
        checkedBorderColor: Color = Color.Transparent,
        checkedIconColor: Color = DmsTheme.colors.secondary,
        uncheckedThumbColor: Color = DmsTheme.colors.surfaceTint,
        uncheckedTrackColor: Color = DmsTheme.colors.onSurface,
        uncheckedBorderColor: Color = Color.Transparent,
        uncheckedIconColor: Color = DmsTheme.colors.onSurface,
    ): SwitchColors =
        SwitchDefaults.colors(
            checkedThumbColor = checkedThumbColor,
            checkedTrackColor = checkedTrackColor,
            checkedBorderColor = checkedBorderColor,
            checkedIconColor = checkedIconColor,
            uncheckedThumbColor = uncheckedThumbColor,
            uncheckedTrackColor = uncheckedTrackColor,
            uncheckedBorderColor = uncheckedBorderColor,
            uncheckedIconColor = uncheckedIconColor,
        )
}
