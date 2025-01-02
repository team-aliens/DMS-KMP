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

@Composable
fun DmsTimer(
    modifier: Modifier = Modifier,
    timerTotalSeconds: Long = 180000L,
    timerInterval: Long = 1000L,
    onTimerFinished: (Boolean) -> Unit,
) {
    var time by remember { mutableStateOf(timerTotalSeconds) }
    var timerFinished by remember { mutableStateOf(false) }

    val countdownTimer = CountDownTimer()

    LaunchedEffect(Unit) {
        countdownTimer.start(
            durationMillis = timerTotalSeconds,
            intervalMillis = timerInterval,
            listener = object : CountDownTimerListener {
                override fun onTick(timeLeft: Long) {
                    time = timeLeft
                }

                override fun onFinish() {
                    timerFinished = true
                    onTimerFinished(timerFinished)
                }
            },
        )
    }

    DmsText(
        modifier = modifier,
        text = formatTime(time / 1000),
        style = DmsTypography.Body1,
        color = DmsTheme.colors.inversePrimary,
    )
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    val time = when {
        minutes > 0 -> "${minutes}분 ${secs}초"
        else -> "${secs}초"
    }
    return time
}
