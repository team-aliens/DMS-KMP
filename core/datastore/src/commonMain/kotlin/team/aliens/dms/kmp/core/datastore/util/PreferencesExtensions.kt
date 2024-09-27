package team.aliens.dms.kmp.core.datastore.util

import androidx.datastore.preferences.core.Preferences

/**
 * Preferences에서 지정된 키에 해당하는 값을 가져옵니다.
 * 값이 없으면 지정된 예외를 던집니다.
 *
 * @param key 가져올 값의 키
 * @param exception 값이 없을 경우 던질 예외
 * @return 키에 저장된 값
 * @throws Exception 지정된 키에 해당하는 값이 없을 경우
 */
fun <T> Preferences.getValueOrThrow(key: Preferences.Key<T>, exception: Exception): T {
    return this[key] ?: throw exception
}
