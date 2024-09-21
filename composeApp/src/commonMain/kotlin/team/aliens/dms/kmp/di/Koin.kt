package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.datastore.di.dataStoreModule
import team.aliens.dms.kmp.core.network.di.networkModule
import team.aliens.dms.kmp.database.di.dataSourceModule

fun appModule() = listOf(
    networkModule,
    dataStoreModule,
    dataStoreModule,
    dataSourceModule,
)

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
