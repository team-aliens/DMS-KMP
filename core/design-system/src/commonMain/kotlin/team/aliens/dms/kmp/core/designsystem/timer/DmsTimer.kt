package team.aliens.dms.kmp.core.designsystem.timer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import team.aliens.dms.kmp.core.common.timer.CountDownTimer
import team.aliens.dms.kmp.core.common.timer.CountDownTimerListener
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

private const val ONE_MINUTE_MILLIS = 60_000L

@Composable
fun DmsTimer(
    modifier: Modifier = Modifier,
    countdownTimer: CountDownTimer,
    timerTotalSeconds: Long = 180000L,
    timerInterval: Long = 1000L,
    onTimerFinished: (Boolean) -> Unit,
) {
    var time by remember { mutableStateOf(timerTotalSeconds) }
    var timerFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        countdownTimer.start()
        countdownTimer.listener =
            object : CountDownTimerListener {
                override fun onTick(timeLeft: Long) {
                    timerFinished = false
                    time = timeLeft
                }

                override fun onFinish() {
                    timerFinished = true
                }
            }
    }

    LaunchedEffect(timerFinished) {
        onTimerFinished(timerFinished)
    }

    val textColor =
        if (time <= ONE_MINUTE_MILLIS) {
            DmsTheme.colors.onErrorContainer
        } else {
            DmsTheme.colors.onPrimaryContainer
        }

    DmsText(
        modifier = modifier,
        text = formatTime(time / 1000),
        style = DmsTypography.BodyB,
        color = textColor,
    )
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    val time =
        when {
            minutes > 0 -> "${minutes}분 ${secs}초"
            else -> "${secs}초"
        }
    return time
}
