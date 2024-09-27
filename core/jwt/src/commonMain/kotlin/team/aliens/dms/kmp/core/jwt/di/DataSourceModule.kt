package team.aliens.dms.kmp.core.jwt.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.jwt.datastore.JwtDataStoreDataSource
import team.aliens.dms.kmp.core.jwt.datastore.JwtDataStoreDataSourceImpl

internal val dataSourceModule = module {
    single<JwtDataStoreDataSource> { JwtDataStoreDataSourceImpl(get()) }
}
