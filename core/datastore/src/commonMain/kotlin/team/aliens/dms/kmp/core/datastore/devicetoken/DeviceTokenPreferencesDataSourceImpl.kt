package team.aliens.dms.kmp.core.datastore.devicetoken

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore

internal class DeviceTokenPreferencesDataSourceImpl(
    private val dataStore: PreferencesDataStore,
) : DeviceTokenPreferencesDataSource {

    override suspend fun loadDeviceToken(): String? =
        dataStore.data.firstOrNull()?.get(DEVICE_TOKEN_KEY)

    override suspend fun storeDeviceToken(token: String) {
        dataStore.edit { it[DEVICE_TOKEN_KEY] = token }
    }

    override suspend fun clearDeviceToken() {
        dataStore.edit { it.remove(DEVICE_TOKEN_KEY) }
    }

    private companion object {
        val DEVICE_TOKEN_KEY = stringPreferencesKey("device_token")
    }
}
