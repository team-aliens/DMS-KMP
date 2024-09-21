package team.aliens.dms.kmp.core.datastore

import androidx.datastore.core.DataMigration
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath

private lateinit var dataStore: PreferencesDataStore

@OptIn(InternalCoroutinesApi::class)
private val lock = SynchronizedObject()

@OptIn(InternalCoroutinesApi::class)
fun getDataStore(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    migrations: List<DataMigration<Preferences>> = listOf(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    producePath: () -> String,
): PreferencesDataStore =
    synchronized(lock) {
        if (::dataStore.isInitialized) {
            dataStore
        } else {
            PreferenceDataStoreFactory
                .createWithPath(
                    corruptionHandler = corruptionHandler,
                    migrations = migrations,
                    scope = scope,
                    produceFile = { producePath().toPath() },
                )
                .also { dataStore = it }
        }
    }

internal const val JWT_STORE = "jwt_store.preferences_pb"
internal const val FEATURES_STORE = "features_store.preferences_pb"
internal const val DEVICE_STORE = "device_store.preferences_pb"
