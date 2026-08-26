package team.aliens.dms.kmp.core.common.util

import android.util.Log

actual fun logd(
    tag: String,
    message: String,
) {
    Log.d(tag, message)
}

actual fun logi(
    tag: String,
    message: String,
) {
    Log.i(tag, message)
}

actual fun loge(
    tag: String,
    message: String,
    throwable: Throwable?,
) {
    Log.e(tag, message, throwable)
}
