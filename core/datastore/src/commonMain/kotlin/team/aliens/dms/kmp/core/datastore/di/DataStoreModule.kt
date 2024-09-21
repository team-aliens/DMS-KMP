package team.aliens.dms.kmp.core.datastore.di

import org.koin.core.module.Module
import org.koin.dsl.module

val dataStoreModule =
    module {
        includes(platformDataStoreModule)
    }

internal expect val platformDataStoreModule: Module
