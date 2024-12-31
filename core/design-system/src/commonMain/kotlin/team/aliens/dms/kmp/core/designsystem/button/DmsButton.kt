package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.DEFAULT_PRESS_DEPTH
import team.aliens.dms.kmp.core.designsystem.util.MIN_PRESS_DEPTH
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.designsystem.util.keyboardAsState

data class ButtonState(
    val enabled: ButtonTheme,
    val pressed: ButtonTheme,
    val disabled: ButtonTheme,
)

enum class ButtonColor {
    Primary,
    Gray,
    Error,
    Refuse,
}

enum class ButtonType {
    Contained,
    Outlined,
    Text,
    Underline,
    Rounded,
}

data class ButtonTheme(
    val textColor: Color,
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
)

@Composable
private fun ButtonColor.containedColors() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.inversePrimary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.secondaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.primaryContainer,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.inverseOnSurface,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.surfaceBright,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.surfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.outline,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.errorContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.onError,
        ),
    )

    ButtonColor.Refuse -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.outline,
            backgroundColor = DmsTheme.colors.error,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.outline,
            backgroundColor = DmsTheme.colors.onError,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.onError,
            backgroundColor = DmsTheme.colors.error,
        ),
    )
}

@Composable
private fun ButtonColor.outlinedColors() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onSecondaryContainer,
            borderColor = DmsTheme.colors.onSecondaryContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            borderColor = DmsTheme.colors.errorContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            borderColor = DmsTheme.colors.onError,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onTertiaryContainer,
            borderColor = DmsTheme.colors.onTertiaryContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceBright,
            borderColor = DmsTheme.colors.surfaceBright,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceVariant,
            borderColor = DmsTheme.colors.surfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.outline,
            borderColor = DmsTheme.colors.outline,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.errorContainer,
            borderColor = DmsTheme.colors.errorContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.onError,
            borderColor = DmsTheme.colors.onError,
        ),
    )

    else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
}

@Composable
private fun ButtonColor.textColors() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.secondary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.onSecondaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.primaryContainer,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onTertiaryContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceBright,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceVariant,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.inversePrimary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.primaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.onPrimary,
        ),
    )

    else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
}

@Composable
private fun ButtonColor.underlineColors() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.secondary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.primaryContainer,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.secondaryContainer,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onTertiaryContainer,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceVariant,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceBright,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.outline,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.onError,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.errorContainer,
        ),
    )

    else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
}

@Composable
private fun ButtonColor.roundedColors() = when (this) {
    ButtonColor.Primary -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onPrimary,
            backgroundColor = DmsTheme.colors.secondary,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.primaryContainer,
            backgroundColor = DmsTheme.colors.primary,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.secondaryContainer,
            backgroundColor = DmsTheme.colors.inversePrimary,
        ),
    )

    ButtonColor.Gray -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.onTertiaryContainer,
            backgroundColor = DmsTheme.colors.surfaceVariant,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.surfaceVariant,
            backgroundColor = DmsTheme.colors.surface,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceBright,
            backgroundColor = DmsTheme.colors.onTertiaryContainer,
        ),
    )

    ButtonColor.Error -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.surfaceTint,
            backgroundColor = DmsTheme.colors.outline,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.error,
            backgroundColor = DmsTheme.colors.onError,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.onError,
            backgroundColor = DmsTheme.colors.errorContainer,
        ),
    )

    ButtonColor.Refuse -> ButtonState(
        enabled = ButtonTheme(
            textColor = DmsTheme.colors.errorContainer,
            backgroundColor = DmsTheme.colors.onError,
        ),
        pressed = ButtonTheme(
            textColor = DmsTheme.colors.onError,
            backgroundColor = DmsTheme.colors.error,
        ),
        disabled = ButtonTheme(
            textColor = DmsTheme.colors.errorContainer,
            backgroundColor = DmsTheme.colors.onErrorContainer,
        ),
    )
}

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    shape: Shape,
    onClick: () -> Unit,
    onPressed: (pressed: Boolean) -> Unit,
    keyboardInteractionEnabled: Boolean,
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

    val keyboardShow by keyboardAsState()
    val isKeyboardHideButton = keyboardShow && keyboardInteractionEnabled
    val (shapeByKeyboardShow, pressDepth) = if (isKeyboardHideButton) {
        RoundedCornerShape(0.dp) to MIN_PRESS_DEPTH
    } else {
        shape to DEFAULT_PRESS_DEPTH
    }
    Box(
        modifier = modifier
            .clip(shape = shapeByKeyboardShow)
            .clickable(
                pressDepth = pressDepth,
                enabled = enabled,
                onPressed = onPressed,
                onClick = onClick,
            ),
    ) {
        content()
    }
}

/*@Composable
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
}*/

/*@Composable
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
}*/

@Composable
fun DmsContainedButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    buttonColor: ButtonColor,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    keyboardInteractionEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val buttonColors = buttonColor.containedColors()
    val backgroundColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttonColors.disabled.backgroundColor!!
        } else if (pressed) {
            buttonColors.pressed.backgroundColor!!
        } else {
            buttonColors.enabled.backgroundColor!!
        },
    )
    val contentColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttonColors.disabled.textColor
        } else if (pressed) {
            buttonColors.pressed.textColor
        } else {
            buttonColors.pressed.textColor
        },
    )

    BasicButton(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        onClick = onClick,
        onPressed = { pressed = it },
        keyboardInteractionEnabled = keyboardInteractionEnabled,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
        ) {
            DmsText(
                text = text,
                style = DmsTypography.Button0,
                color = contentColor,
            )
        }
    }
}

@Composable
fun DmsButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonType: ButtonType,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(4.dp),
    buttonColor: ButtonColor,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
    keyboardInteractionEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val buttonColors = when (buttonType) {
        ButtonType.Contained -> buttonColor.containedColors()
        ButtonType.Outlined -> buttonColor.outlinedColors()
        ButtonType.Text -> buttonColor.textColors()
        ButtonType.Underline -> buttonColor.underlineColors()
        ButtonType.Rounded -> buttonColor.roundedColors()
    }

    val backgroundColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttonColors.disabled.backgroundColor ?: Color.Transparent
        } else if (pressed) {
            buttonColors.pressed.backgroundColor ?: Color.Transparent
        } else {
            buttonColors.enabled.backgroundColor ?: Color.Transparent
        },
    )
    val borderColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttonColors.disabled.borderColor ?: Color.Transparent
        } else if (pressed) {
            buttonColors.pressed.borderColor ?: Color.Transparent
        } else {
            buttonColors.enabled.borderColor ?: Color.Transparent
        },
    )
    val contentColor by animateColorAsState(
        targetValue = if (!enabled) {
            buttonColors.disabled.textColor
        } else if (pressed) {
            buttonColors.pressed.textColor
        } else {
            buttonColors.enabled.textColor
        },
    )

    val buttonShape = if (buttonType == ButtonType.Rounded) RoundedCornerShape(24.dp) else shape

    BasicButton(
        modifier = modifier,
        enabled = enabled,
        shape = buttonShape,
        onClick = onClick,
        onPressed = { pressed = it },
        keyboardInteractionEnabled = keyboardInteractionEnabled,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .border(
                    buttonType = buttonType,
                    width = 1.dp,
                    shape = buttonShape,
                    color = borderColor,
                )
                .padding(contentPadding),
            horizontalArrangement = Arrangement.Center,
        ) {
            DmsText(
                text = text,
                style = if (buttonType == ButtonType.Underline) DmsTypography.Button3 else DmsTypography.Button0,
                color = contentColor,
                textDecoration = if (buttonType == ButtonType.Underline) TextDecoration.Underline else TextDecoration.None,
            )
        }
    }
}

@Composable
private fun Modifier.border(
    buttonType: ButtonType,
    width: Dp,
    shape: Shape,
    color: Color,
): Modifier {
    return if (buttonType == ButtonType.Outlined) {
        Modifier.border(
            width = width,
            shape = shape,
            color = color,
        )
    } else {
        Modifier
    }
}
