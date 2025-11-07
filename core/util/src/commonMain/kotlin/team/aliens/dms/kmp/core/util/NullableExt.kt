package team.aliens.dms.kmp.core.util

inline fun <T : Any?> guardAll(vararg values: T?, onNull: () -> Nothing): List<T> {
    return values.map { it ?: onNull() }
}

fun <A : Any, B : Any> guardAll(
    a: A?,
    b: B?,
): Pair<A, B>? = if (a != null && b != null) a to b else null

fun <A : Any, B : Any, C : Any> guardAll(
    a: A?,
    b: B?,
    c: C?,
): Triple<A, B, C>? =
    if (a != null && b != null && c != null) Triple(a, b, c) else null
