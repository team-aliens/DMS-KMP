package team.aliens.dms.kmp.core.datastore.util

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

internal suspend inline fun <reified T> transform(
    onSuccess: (T) -> Unit = {},
    onFailure: (Throwable) -> Unit = { throw TransformFailureException() },
    crossinline block: suspend () -> T,
) = runCatching { block() }
    .onSuccess(onSuccess)
    .onFailure(onFailure)
    .getOrThrow()
