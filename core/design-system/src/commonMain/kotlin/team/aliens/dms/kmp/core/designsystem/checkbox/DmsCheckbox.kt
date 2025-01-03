package team.aliens.dms.kmp.core.designsystem.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
fun DmsCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = DmsCheckboxDefaults.Colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        interactionSource = interactionSource,
    )
}

object DmsCheckboxDefaults {

    private const val DISABLED_CONTAINER_OPACITY = 0.38f

    @Composable
    fun Colors(
        checkedColor: Color = DmsTheme.colors.secondary,
        uncheckedColor: Color = DmsTheme.colors.inverseSurface,
        checkmarkColor: Color = DmsTheme.colors.surfaceTint,
        disabledColor: Color = DmsTheme.colors.primaryContainer,
        disabledIndeterminateColor: Color = checkedColor.copy(alpha = DISABLED_CONTAINER_OPACITY),
    ): CheckboxColors = CheckboxDefaults.colors(
        checkedColor = checkedColor,
        uncheckedColor = uncheckedColor,
        checkmarkColor = checkmarkColor,
        disabledColor = disabledColor,
        disabledIndeterminateColor = disabledIndeterminateColor,
    )
}
