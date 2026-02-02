package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.data.di.repositoryModule
import team.aliens.dms.kmp.core.database.di.databaseModule
import team.aliens.dms.kmp.core.datastore.di.dataStoreModule
import team.aliens.dms.kmp.core.domain.di.domainModule
import team.aliens.dms.kmp.core.network.di.networkDataSourceModule
import team.aliens.dms.kmp.core.network.di.networkModule
import team.aliens.dms.kmp.core.notification.di.notificationPlatformModule

fun appModule() =
    listOf(
        networkModule,
        dataStoreModule,
        networkDataSourceModule,
        databaseModule,
        repositoryModule,
        featureModule,
        domainModule,
        notificationPlatformModule,
    )

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
