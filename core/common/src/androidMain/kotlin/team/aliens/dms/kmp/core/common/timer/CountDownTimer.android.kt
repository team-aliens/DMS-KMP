package team.aliens.dms.kmp.core.common.timer

import android.os.CountDownTimer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CountDownTimer actual constructor(
    private val durationMillis: Long,
    private val intervalMillis: Long,
) {
    private val timer: CountDownTimer =
        object : CountDownTimer(durationMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                listener?.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                _isRunning.value = false
                listener?.onFinish()
            }
        }
    private var _isRunning = MutableStateFlow(false)

    actual var listener: CountDownTimerListener? = null
    actual val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    actual fun start() {
        _isRunning.value = true
        timer.start()
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
        timer.cancel()
    }
}
