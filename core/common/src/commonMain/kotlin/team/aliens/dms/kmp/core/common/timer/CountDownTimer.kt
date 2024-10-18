package team.aliens.dms.kmp.core.common.timer

interface CountDownTimerListener {
    fun onTick(timeLeft: Long)
    fun onFinish()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CountDownTimer() {
    fun start(
        durationMillis: Long,
        intervalMillis: Long,
        listener: CountDownTimerListener,
    )

    fun stop()
}
