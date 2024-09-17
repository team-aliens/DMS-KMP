package team.aliens.dms.kmp.core.datastore

import java.nio.file.Paths

internal class DesktopDataStore {
    fun getJwtDataStore(): PreferencesDataStore = getDataStore(
        producePath = { "datastore/$JWT_STORE" }
    )

    fun getFeaturesDataStore(): PreferencesDataStore = getDataStore(
        producePath = { "datastore/$FEATURES_STORE" }
    )

    fun getDeviceDataStore(): PreferencesDataStore = getDataStore(
        producePath = { "datastore/$DEVICE_STORE" }
    )
}
