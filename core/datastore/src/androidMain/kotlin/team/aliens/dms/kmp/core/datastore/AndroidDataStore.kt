package team.aliens.dms.kmp.core.datastore

import android.content.Context

internal class DataStore(private val context: Context) {

    fun getJwtDataStore(): PreferencesDataStore = getDataStore(
        producePath = { context.filesDir.resolve("datastore/$JWT_STORE").absolutePath },
    )

    fun getFeaturesStore(): PreferencesDataStore = getDataStore(
        producePath = { context.filesDir.resolve("datastore/$FEATURES_STORE").absolutePath },
    )

    fun getDeviceStore(): PreferencesDataStore = getDataStore(
        producePath = { context.filesDir.resolve("datastore/$DEVICE_STORE").absolutePath },
    )
}
