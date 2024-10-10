package team.aliens.dms.kmp.di

import org.koin.core.context.startKoin
import team.aliens.dms.kmp.core.datastore.di.dataStoreModule
import team.aliens.dms.kmp.core.domain.di.domainModule
import team.aliens.dms.kmp.core.jwt.di.jwtModule
import team.aliens.dms.kmp.core.network.di.networkModule
import team.aliens.dms.kmp.database.di.databaseModule

fun appModule() =
    listOf(
        networkModule,
        dataStoreModule,
        databaseModule,
        jwtModule,
        featureModule,
        domainModule,
    )

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
