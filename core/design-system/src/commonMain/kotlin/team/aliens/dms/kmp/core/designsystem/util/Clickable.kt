package team.aliens.dms.kmp.core.designsystem.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

internal const val DURATION_MILLIS = 100
internal const val DEFAULT_PRESS_DEPTH = 0.98f
internal const val MIN_PRESS_DEPTH = 1f
internal const val DEFAULT_DISABLED_MILLIS = 300L

@Composable
fun Modifier.clickable(
    enabled: Boolean = true,
    pressDepth: Float = DEFAULT_PRESS_DEPTH,
    indication: Indication = ripple(),
    onPressed: ((pressed: Boolean) -> Unit)? = null,
    onClick: () -> Unit,
    disabledMillis: Long = DEFAULT_DISABLED_MILLIS,
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) {
            pressDepth
        } else {
            1f
        },
//        animationSpec = tween(delayMillis = DURATION_MILLIS),
    )

    LaunchedEffect(isPressed) {
        onPressed?.invoke(isPressed)
    }

    return this
        .clickable(
            onClick = onClick,
            enabled = enabled,
            indication = indication,
            interactionSource = interactionSource,
        )
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
        )
}
