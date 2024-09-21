package team.aliens.dms.kmp.core.database.di

import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.database.DriverFactory

internal actual val platformDatabaseModule: Module =
    module {
        single { DriverFactory(get()) }
    }
