package team.aliens.dms.kmp.core.jwt.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.jwt.datastore.store.JwtDataStore
import team.aliens.dms.kmp.core.jwt.datastore.store.JwtDataStoreImpl

internal val storeModule = module {
    single<JwtDataStore> { JwtDataStoreImpl(get()) }
}
