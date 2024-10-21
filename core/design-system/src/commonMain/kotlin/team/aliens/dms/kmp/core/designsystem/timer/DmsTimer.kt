package team.aliens.dms.kmp.core.designsystem.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.common.timer.CountDownTimer
import team.aliens.dms.kmp.core.common.timer.CountDownTimerListener
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsTimer(
    modifier: Modifier = Modifier,
    timerTotalSeconds: Long = 180000L,
    timerInterval: Long = 1000L,
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
                }
            },
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            painter = painterResource(DmsIcon.Alarm),
            contentDescription = "alarm",
            tint = DmsTheme.colors.onSurface,
        )
        DmsText(
            text = formatTime(time / 1000),
            style = DmsTypography.Body1Medium,
            color = DmsTheme.colors.onSurface,
        )
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "$minutes:$secs"
}
