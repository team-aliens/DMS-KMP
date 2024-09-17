package team.aliens.dms.kmp.core.datastore.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.DataStore

internal actual val platformDataStoreModule = module {
    single { DataStore(context = get()).getJwtDataStore() }
    single { DataStore(context = get()).getFeaturesStore() }
    single { DataStore(context = get()).getDeviceStore() }
}
