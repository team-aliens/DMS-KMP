package team.aliens.dms.kmp.core.datastore.remain

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore

internal class RemainPreferencesDataSourceImpl(
    private val preferencesDataStore: PreferencesDataStore,
) : RemainPreferencesDataSource {
    override suspend fun loadRemain(): Result<String> =
        runCatching {
            preferencesDataStore.data.firstOrNull()?.get(REMAIN_APPLICATION) ?: ""
        }

    override suspend fun storeRemain(remain: String): Result<Unit> =
        runCatching {
            preferencesDataStore.edit { preferences ->
                preferences[REMAIN_APPLICATION] = remain
            }
        }

    override suspend fun clearRemain(): Result<Unit> =
        runCatching {
            preferencesDataStore.edit { preferences ->
                preferences.remove(REMAIN_APPLICATION)
            }
        }

    private companion object {
        val REMAIN_APPLICATION = stringPreferencesKey("remain_application")
    }
}
