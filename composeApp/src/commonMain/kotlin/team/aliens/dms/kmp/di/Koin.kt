package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.datastore.di.dataStoreModule
import team.aliens.dms.kmp.core.network.di.networkModule

fun appModule() = listOf(
    networkModule,
    dataStoreModule,
    dataStoreModule,
)

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
