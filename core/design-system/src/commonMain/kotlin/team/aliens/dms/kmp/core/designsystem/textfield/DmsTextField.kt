package team.aliens.dms.kmp.core.designsystem.textfield

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
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
    hint: String = "",
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    isError: Boolean = false,
    errorMessage: String? = null,
    showVisibleIcon: Boolean = false,
    showClearIcon: Boolean = false,
) {
    var isFocused by remember { mutableStateOf(false) }
    val labelColor by animateColorAsState(
        targetValue = if (isError) {
            DmsTheme.colors.onErrorContainer
        } else if (isFocused || value.isNotEmpty()) {
            DmsTheme.colors.onPrimaryContainer
        } else {
            DmsTheme.colors.surfaceContainer
        },
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        label?.let { label ->
            DmsText(
                text = label,
                style = DmsTypography.labelM,
                color = labelColor,
            )
        }
        TextField(
            modifier = Modifier.onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
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
            isFocused = isFocused,
        )
        if (isError) {
            errorMessage?.let { errorMessage ->
                DmsText(
                    text = errorMessage,
                    style = DmsTypography.labelM,
                    color = DmsTheme.colors.errorContainer,
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
    isFocused: Boolean,
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
    val lineColor by animateColorAsState(
        targetValue = if (isError) {
            DmsTheme.colors.onErrorContainer
        } else if (isFocused || value.isNotEmpty()) {
            DmsTheme.colors.onPrimaryContainer
        } else {
            DmsTheme.colors.onSurfaceVariant
        },
    )
    val focusManager = LocalFocusManager.current

    BasicTextField(
        value = value.take(maxLength),
        onValueChange = { newValue ->
            val filtered = newValue.replace("\t", "")
            if (filtered.length <= maxLength) {
                onValueChange(filtered)
            }
        },
        modifier = modifier.onKeyEvent { keyEvent ->
            if (keyEvent.key == Key.Tab && keyEvent.type == KeyEventType.KeyDown) {
                focusManager.moveFocus(FocusDirection.Next)
                true
            } else {
                false
            }
        },
        textStyle = DmsTypography.BodyM,
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
        cursorBrush = SolidColor(DmsTheme.colors.onBackground),
    ) { innerTextField ->
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    innerTextField()
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        DmsText(
                            modifier = Modifier.alpha(hintAlpha),
                            text = hint,
                            style = DmsTypography.Body1,
                            color = DmsTheme.colors.inverseOnSurface,
                        )
                    }
                }
                Row(
                    modifier = Modifier.height(24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (showVisibleIcon) {
                        DmsIconButton(
                            resource = icon,
                            tint = DmsTheme.colors.inverseSurface,
                            onClick = { visible = !visible },
                        )
                    }
                    if (showClearIcon && value.isNotEmpty()) {
                        DmsIconButton(
                            resource = DmsIcon.Cancel,
                            tint = DmsTheme.colors.inverseSurface,
                            onClick = { onValueChange("") },
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 2.dp,
                color = lineColor,
            )
        }
    }
}
