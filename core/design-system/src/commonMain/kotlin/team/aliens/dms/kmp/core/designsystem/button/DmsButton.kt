package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.indicator.DmsDotsLoadingIndicator
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.DEFAULT_PRESS_DEPTH
import team.aliens.dms.kmp.core.designsystem.util.MIN_PRESS_DEPTH
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.designsystem.util.keyboardAsState
import team.aliens.dms.kmp.core.designsystem.util.modifyIf

data class ButtonState(
    val enabled: ButtonTheme,
    val pressed: ButtonTheme,
    val disabled: ButtonTheme,
)

enum class ButtonColor {
    Primary,
    Gray,
    Error,
}

enum class ButtonType {
    Contained,
    Text,
    Underline,
}

data class ButtonTheme(
    val textColor: Color,
    val backgroundColor: Color? = null,
    val borderColor: Color? = null,
)

@Composable
private fun ButtonColor.containedColors() =
    when (this) {
        ButtonColor.Primary ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = Color.White,
                        backgroundColor = DmsTheme.colors.onPrimaryContainer,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = Color.White,
                        backgroundColor = DmsTheme.colors.inversePrimary,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = Color.White,
                        backgroundColor = DmsTheme.colors.onPrimary,
                    ),
            )

        ButtonColor.Gray ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.surfaceTint,
                        backgroundColor = DmsTheme.colors.inverseOnSurface,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.surfaceTint,
                        backgroundColor = DmsTheme.colors.surfaceBright,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.surfaceTint,
                        backgroundColor = DmsTheme.colors.surfaceVariant,
                    ),
            )

        ButtonColor.Error ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.surface,
                        backgroundColor = DmsTheme.colors.onErrorContainer,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.surface,
                        backgroundColor = DmsTheme.colors.onErrorContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.inverseSurface,
                        backgroundColor = DmsTheme.colors.outlineVariant,
                    ),
            )
    }

@Composable
private fun ButtonColor.textColors() =
    when (this) {
        ButtonColor.Primary ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.inverseSurface,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.onSecondaryContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.primaryContainer,
                    ),
            )

        ButtonColor.Gray ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.inverseSurface,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.tertiaryContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.onSurfaceVariant,
                    ),
            )

        ButtonColor.Error ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.inversePrimary,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.primaryContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.onPrimary,
                    ),
            )

        else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
    }

@Composable
private fun ButtonColor.underlineColors() =
    when (this) {
        ButtonColor.Primary ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.secondary,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.secondaryContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.primaryContainer,
                    ),
            )

        ButtonColor.Gray ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.inverseSurface,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.tertiaryContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.onSurfaceVariant,
                    ),
            )

        ButtonColor.Error ->
            ButtonState(
                enabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.outline,
                    ),
                pressed =
                    ButtonTheme(
                        textColor = DmsTheme.colors.errorContainer,
                    ),
                disabled =
                    ButtonTheme(
                        textColor = DmsTheme.colors.onError,
                    ),
            )

        else -> throw IllegalArgumentException("Unhandled ButtonColor: $this")
    }

@Composable
private fun BasicButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    enabled: Boolean,
    shape: Shape,
    borderColor: Color,
    buttonType: ButtonType,
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
    val (shapeByKeyboardShow, pressDepth) =
        if (isKeyboardHideButton) {
            RoundedCornerShape(0.dp) to MIN_PRESS_DEPTH
        } else {
            shape to DEFAULT_PRESS_DEPTH
        }
    val padding =
        if (isKeyboardHideButton) {
            PaddingValues(
                vertical = 0.dp,
                horizontal = 0.dp,
            )
        } else {
            PaddingValues(
                vertical = 12.dp,
                horizontal = 24.dp,
            )
        }

    Box(
        modifier =
            modifier
                .modifyIf(keyboardInteractionEnabled) {
                    padding(padding)
                }
                .clip(shape = shapeByKeyboardShow)
                .background(color = backgroundColor, shape = shapeByKeyboardShow)
                .clickable(
                    pressDepth = pressDepth,
                    enabled = enabled,
                    onPressed = onPressed,
                    onClick = onClick,
                )
                .modifyIf(keyboardInteractionEnabled) {
                    imePadding()
                },
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@Composable
fun DmsButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonType: ButtonType,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    buttonColor: ButtonColor,
    contentPadding: PaddingValues? = null,
    keyboardInteractionEnabled: Boolean = false,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    var pressed by remember { mutableStateOf(false) }

    val buttonColors =
        when (buttonType) {
            ButtonType.Contained -> buttonColor.containedColors()
            ButtonType.Text -> buttonColor.textColors()
            ButtonType.Underline -> buttonColor.underlineColors()
        }

    val backgroundColor by animateColorAsState(
        targetValue =
            if (!enabled) {
                buttonColors.disabled.backgroundColor ?: Color.Transparent
            } else if (pressed) {
                buttonColors.pressed.backgroundColor ?: Color.Transparent
            } else {
                buttonColors.enabled.backgroundColor ?: Color.Transparent
            },
    )
    val borderColor by animateColorAsState(
        targetValue =
            if (!enabled) {
                buttonColors.disabled.borderColor ?: Color.Transparent
            } else if (pressed) {
                buttonColors.pressed.borderColor ?: Color.Transparent
            } else {
                buttonColors.enabled.borderColor ?: Color.Transparent
            },
    )
    val contentColor by animateColorAsState(
        targetValue =
            if (!enabled) {
                buttonColors.disabled.textColor
            } else if (pressed) {
                buttonColors.pressed.textColor
            } else {
                buttonColors.enabled.textColor
            },
    )
    val innerPadding =
        if (buttonType == ButtonType.Text || buttonType == ButtonType.Underline) {
            PaddingValues(
                horizontal = 8.dp,
                vertical = 6.dp,
            )
        } else {
            PaddingValues(horizontal = 20.dp, vertical = 16.dp)
        }

    // val buttonShape = if (buttonType == ButtonType.Rounded) RoundedCornerShape(24.dp) else shape

    BasicButton(
        modifier = modifier,
        backgroundColor = backgroundColor,
        enabled = enabled && !isLoading,
        shape = shape,
        borderColor = borderColor,
        buttonType = buttonType,
        onClick = onClick,
        onPressed = { pressed = it },
        keyboardInteractionEnabled = keyboardInteractionEnabled,
    ) {
        val padding = contentPadding ?: innerPadding
        val textStyle =
            if (buttonType == ButtonType.Underline || buttonType == ButtonType.Text) {
                DmsTypography.labelM
            } else {
                DmsTypography.BodyB
            }
        val size = with(LocalDensity.current) { textStyle.fontSize.toDp() * 1.2f }

        Box(
            modifier =
                Modifier
                    .padding(padding)
                    .height(size),
            contentAlignment = Alignment.Center,
        ) {
            if (isLoading) {
                DmsDotsLoadingIndicator(
                    activeColor = contentColor,
                )
            } else {
                DmsText(
                    text = text,
                    style = textStyle,
                    color = contentColor,
                    textDecoration = if (buttonType == ButtonType.Underline) TextDecoration.Underline else TextDecoration.None,
                )
            }
        }
    }
}
