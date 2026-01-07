package team.aliens.dms.kmp.core.common.timer

import kotlinx.coroutines.flow.StateFlow

interface CountDownTimerListener {
    fun onTick(timeLeft: Long)
    fun onFinish()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CountDownTimer(
    durationMillis: Long = 180000L,
    intervalMillis: Long = 1000L,
) {
    var listener: CountDownTimerListener?
    val isRunning: StateFlow<Boolean>

    fun start()

    fun restart()

    fun stop()
}
