package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.network.di.networkModule

fun appModule() = listOf(
    networkModule
)

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
