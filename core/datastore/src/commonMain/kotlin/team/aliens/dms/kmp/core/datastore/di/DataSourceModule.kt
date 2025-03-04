package team.aliens.dms.kmp.core.datastore.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSourceImpl

internal val dataSourceModule = module {
    singleOf(::AuthPreferencesDataSourceImpl) { bind<AuthPreferencesDataSource>() }
}
