package team.aliens.dms.kmp.core.common.util

expect fun logd(
    tag: String,
    message: String,
)

expect fun logi(
    tag: String,
    message: String,
)

expect fun loge(
    tag: String,
    message: String,
    throwable: Throwable? = null,
)
