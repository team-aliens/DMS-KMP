package team.aliens.dms.kmp.core.datastore

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
internal class IosDataStore {
    fun getJwtDataStore(): PreferencesDataStore =
        getDataStore(
            producePath = {
                val documentDirectory: NSURL? =
                    NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                requireNotNull(documentDirectory).path() + "/$JWT_STORE"
            },
        )

    fun getFeaturesDataStore(): PreferencesDataStore =
        getDataStore(
            producePath = {
                val documentDirectory: NSURL? =
                    NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                requireNotNull(documentDirectory).path() + "/$FEATURES_STORE"
            },
        )

    fun getDeviceDataStore(): PreferencesDataStore =
        getDataStore(
            producePath = {
                val documentDirectory: NSURL? =
                    NSFileManager.defaultManager.URLForDirectory(
                        directory = NSDocumentDirectory,
                        inDomain = NSUserDomainMask,
                        appropriateForURL = null,
                        create = false,
                        error = null,
                    )
                requireNotNull(documentDirectory).path() + "/$DEVICE_STORE"
            },
        )
}
