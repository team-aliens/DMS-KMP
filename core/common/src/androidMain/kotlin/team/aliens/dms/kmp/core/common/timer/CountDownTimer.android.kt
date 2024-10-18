package team.aliens.dms.kmp.core.common.timer

import android.os.CountDownTimer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CountDownTimer {
    private var timer: CountDownTimer? = null

    actual fun start(
        durationMillis: Long,
        intervalMillis: Long,
        listener: CountDownTimerListener,
    ) {
        timer = object : CountDownTimer(durationMillis,intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                listener.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                listener.onFinish()
            }
        }.start()
    }

    actual fun stop() {
        timer?.cancel()
    }
}
