package team.aliens.dms.kmp.core.database.di

import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule = module {
    includes(platformDatabaseModule)
}

internal expect val platformDatabaseModule: Module
