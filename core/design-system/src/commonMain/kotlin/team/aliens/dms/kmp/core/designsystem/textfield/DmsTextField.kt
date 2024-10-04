package team.aliens.dms.kmp.core.designsystem.textfield

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsTextField(
    modifier: Modifier = Modifier,
    label: String? = null,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    description: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    isError: Boolean = false,
    errorDescription: String? = null,
    showVisibleIcon: Boolean = false,
    showClearIcon: Boolean = false,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        label?.let { label ->
            DmsText(
                text = label,
                style = DmsTypography.Body1Medium,
            )
        }
        TextField(
            modifier = modifier,
            value = value,
            hint = hint,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            singleLine = singleLine,
            imeAction = imeAction,
            keyboardType = keyboardType,
            keyboardActions = keyboardActions,
            maxLength = maxLength,
            isError = isError,
            showVisibleIcon = showVisibleIcon,
            showClearIcon = showClearIcon,
        )
        if (isError) {
            errorDescription?.let { errorDescription ->
                DmsText(
                    text = errorDescription,
                    style = DmsTypography.Body1Medium,
                    color = DmsTheme.colors.outlineVariant,
                )
            }
        } else {
            description?.let { description ->
                DmsText(
                    text = description,
                    style = DmsTypography.Body1Medium,
                )
            }
        }
    }
}

@Composable
private fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    readOnly: Boolean,
    singleLine: Boolean,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    maxLength: Int,
    isError: Boolean,
    showVisibleIcon: Boolean,
    showClearIcon: Boolean,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val hintAlpha by animateFloatAsState(
        targetValue = if (value.isEmpty()) {
            1f
        } else {
            0f
        },
    )
    var visible by remember { mutableStateOf(false) }
    val (visualTransformation, icon) = if (visible || !showVisibleIcon) {
        VisualTransformation.None to DmsIcon.Visibility
    } else {
        PasswordVisualTransformation() to DmsIcon.VisibilityOff
    }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val borderStroke = BorderStroke(
        width = 1.dp,
        color = if (isError) {
            DmsTheme.colors.outlineVariant
        } else if (isFocused || value.isNotEmpty()) {
            DmsTheme.colors.inversePrimary
        } else {
            DmsTheme.colors.surface
        },
    )

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = DmsTheme.colors.background,
        border = borderStroke,
    ) {
        BasicTextField(
            value = value.take(maxLength),
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier.padding(16.dp),
            textStyle = DmsTypography.Body1SemiBold,
            singleLine = singleLine,
            enabled = enabled,
            readOnly = readOnly,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
            ),
            keyboardActions = keyboardActions,
            interactionSource = interactionSource,
        ) { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        DmsText(
                            modifier = Modifier.alpha(hintAlpha),
                            text = hint,
                            style = DmsTypography.Body1SemiBold,
                            color = DmsTheme.colors.surface,
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (showVisibleIcon) {
                        DmsIconButton(
                            resource = icon,
                            tint = DmsTheme.colors.surfaceContainerLow,
                            onClick = { visible = !visible },
                        )
                    }
                    if (showClearIcon) {
                        DmsIconButton(
                            resource = DmsIcon.Cancel,
                            tint = DmsTheme.colors.surface,
                            onClick = { onValueChange("") }
                        )
                    }
                }
            }
        }
    }
}
