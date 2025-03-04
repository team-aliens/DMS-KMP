package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.data.di.repositoryModule
import team.aliens.dms.kmp.core.datastore.di.dataStoreModule
import team.aliens.dms.kmp.core.domain.di.domainModule
import team.aliens.dms.kmp.core.network.di.networkDataSourceModule
import team.aliens.dms.kmp.core.network.di.networkModule
import team.aliens.dms.kmp.database.di.databaseModule

fun appModule() =
    listOf(
        networkModule,
        dataStoreModule,
        networkDataSourceModule,
        databaseModule,
        repositoryModule,
        featureModule,
        domainModule,
    )

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
