package team.aliens.dms.kmp.core.designsystem.util

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import team.aliens.dms.kmp.core.util.timeNow

internal const val DURATION_MILLIS = 200
internal const val DEFAULT_PRESS_DEPTH = 0.98f
internal const val MIN_PRESS_DEPTH = 1f
internal const val DEFAULT_DISABLED_MILLIS = 300L

@Composable
fun Modifier.clickable(
    enabled: Boolean = true,
    pressDepth: Float = DEFAULT_PRESS_DEPTH,
    onPressed: ((pressed: Boolean) -> Unit)? = null,
    onClick: () -> Unit,
    disabledMillis: Long = DEFAULT_DISABLED_MILLIS,
): Modifier {
    var pressed by remember { mutableStateOf(false) }
    var lastClick by remember { mutableLongStateOf(0L) }
    val interactionSource = remember { MutableInteractionSource() }
    val scale by animateFloatAsState(
        targetValue = if (pressed) {
            pressDepth
        } else {
            1f
        },
        animationSpec = tween(delayMillis = DURATION_MILLIS),
    )

    return this then Modifier
        .graphicsLayer(
            scaleX = scale,
            scaleY = scale,
        )
        .indication(
            interactionSource = interactionSource,
            indication = ripple(
                bounded = true,
                color = Color.Red,
            ),
        )
        .pointerInput(Unit) {
            if (enabled) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        event.changes.forEach { change ->
                            when {
                                change.changedToDown() -> {
                                    pressed = true
                                    onPressed?.invoke(true)
                                }

                                change.changedToUp() || change.isConsumed -> {
                                    // TODO: 클릭 처리 방식 변경 필요
                                    /*pressed = false
                                    onPressed?.invoke(false)
                                    if (change.changedToUp() && timeNow.toNanosecondOfDay() - lastClick >= disabledMillis) {
                                        lastClick = timeNow.toNanosecondOfDay()
                                        onClick()
                                    }*/
                                    val isInside = change.position.x in 0f..size.width.toFloat() &&
                                            change.position.y in 0f..size.height.toFloat()

                                    pressed = false
                                    onPressed?.invoke(false)

                                    // 클릭 범위 내부에서만 onClick 실행
                                    if (
                                        isInside &&
                                        change.changedToUp() &&
                                        timeNow.toNanosecondOfDay() - lastClick >= disabledMillis
                                    ) {
                                        lastClick = timeNow.toNanosecondOfDay()
                                        onClick()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}
