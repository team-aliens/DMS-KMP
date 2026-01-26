package team.aliens.dms.kmp.core.common.timer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import team.aliens.dms.kmp.core.common.timer.CountDownTimerListener

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class CountDownTimer actual constructor(
    private val durationMillis: Long,
    private val intervalMillis: Long,
) {
    actual var listener: CountDownTimerListener? = null
    actual val isRunning: StateFlow<Boolean> = MutableStateFlow(false)

    actual fun start() {}

    actual fun restart() {}

    actual fun stop() {}
}
