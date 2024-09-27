package team.aliens.dms.kmp.core.datastore.util

import androidx.datastore.preferences.core.Preferences

fun <T> Preferences.getValueOrThrow(key: Preferences.Key<T>, exception: Exception): T {
    return this[key] ?: throw exception
}
