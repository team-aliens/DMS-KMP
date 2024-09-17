package team.aliens.dms.kmp.core.datastore.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.DesktopDataStore

internal actual val platformDataStoreModule = module {
    single { DesktopDataStore().getJwtDataStore() }
    single { DesktopDataStore().getFeaturesDataStore() }
    single { DesktopDataStore().getDeviceDataStore() }
}
