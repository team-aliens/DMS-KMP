package team.aliens.dms.kmp.core.common.timer

import platform.Foundation.NSTimer
import kotlin.native.concurrent.freeze

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CountDownTimer {
    private var timer: NSTimer? = null
    private var timeLeft: Long = 0

    actual fun start(
        durationMillis: Long,
        intervalMillis: Long,
        listener: CountDownTimerListener,
    ) {
        timeLeft = durationMillis
        listener.freeze()
        val intervalSeconds = intervalMillis / 1000.0

        timer = NSTimer.scheduledTimerWithTimeInterval(intervalSeconds, true) {
            timeLeft -= intervalMillis
            if (timeLeft > 0) {
                listener.onTick(timeLeft)
            } else {
                listener.onFinish()
                stop()
            }
        }
    }

    actual fun stop() {
        timer?.invalidate()
        timer = null
    }
}
