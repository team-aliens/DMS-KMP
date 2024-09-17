package team.aliens.dms.kmp.core.datastore.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.IosDataStore

internal actual val platformDataStoreModule = module {
    single { IosDataStore().getJwtDataStore() }
    single { IosDataStore().getFeaturesDataStore() }
    single { IosDataStore().getDeviceDataStore() }
}
