package team.aliens.dms.kmp.core.datastore

import android.content.Context

internal class AndroidDataStore(private val context: Context) {
    fun getAuthDataStore(): PreferencesDataStore =
        getDataStore(
            producePath = { context.filesDir.resolve("datastore/$AUTH_STORE").absolutePath },
        )

    fun getFeaturesStore(): PreferencesDataStore =
        getDataStore(
            producePath = { context.filesDir.resolve("datastore/$FEATURES_STORE").absolutePath },
        )

    fun getDeviceStore(): PreferencesDataStore =
        getDataStore(
            producePath = { context.filesDir.resolve("datastore/$DEVICE_STORE").absolutePath },
        )
}
