package team.aliens.dms.kmp.core.common.timer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Foundation.NSTimer

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CountDownTimer actual constructor(
    private val durationMillis: Long,
    private val intervalMillis: Long,
) {
    private var timer: NSTimer? = null
    private var timeLeft: Long = 0
    private var _isRunning = MutableStateFlow(false)

    actual var listener: CountDownTimerListener? = null
    actual val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    actual fun start() {
        _isRunning.value = true
        timeLeft = durationMillis

        val intervalSeconds = intervalMillis / 1000.0
        timer = NSTimer.scheduledTimerWithTimeInterval(
            interval = intervalSeconds,
            repeats = true,
        ) {
            timeLeft -= intervalMillis
            if (timeLeft >= 0) {
                listener?.onTick(timeLeft)
            } else {
                _isRunning.value = false
                listener?.onFinish()
                it?.invalidate()
                stop()
            }
        }
    }

    actual fun restart() {
        if (isRunning.value) {
            stop()
            start()
        } else {
            start()
        }
    }

    actual fun stop() {
        _isRunning.value = false
        timer?.invalidate()
        timer = null
    }
}
