package team.aliens.dms.kmp.core.network.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.network.auth.datasource.KtorAuthDataSource
import team.aliens.dms.kmp.core.network.auth.datasource.NetworkAuthDataSource

val networkDataSourceModule = module {
    single<NetworkAuthDataSource> { KtorAuthDataSource(get()) }
}
