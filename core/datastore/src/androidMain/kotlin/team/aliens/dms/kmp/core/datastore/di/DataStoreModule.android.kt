package team.aliens.dms.kmp.core.datastore.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.AndroidDataStore

internal actual val platformDataStoreModule =
    module {
        single { AndroidDataStore(context = get()).getJwtDataStore() }
        single { AndroidDataStore(context = get()).getFeaturesStore() }
        single { AndroidDataStore(context = get()).getDeviceStore() }
    }
