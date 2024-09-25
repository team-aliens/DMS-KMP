package team.aliens.dms.kmp.core.datastore.di

import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.jwt.JwtDataSource
import team.aliens.dms.kmp.core.datastore.jwt.JwtDataSourceImpl

val dataStoreModule =
    module {
        single<JwtDataSource> { JwtDataSourceImpl(get()) }

        includes(platformDataStoreModule)
    }

internal expect val platformDataStoreModule: Module
