package team.aliens.dms.kmp.core.datastore.devicetoken

interface DeviceTokenPreferencesDataSource {
    suspend fun loadDeviceToken(): String?

    suspend fun storeDeviceToken(token: String)

    suspend fun clearDeviceToken()
}
