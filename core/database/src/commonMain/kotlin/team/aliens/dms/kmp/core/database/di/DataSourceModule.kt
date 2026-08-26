package team.aliens.dms.kmp.core.database.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.database.datasource.meal.MealDatabaseDataSource
import team.aliens.dms.kmp.core.database.datasource.meal.MealDatabaseDataSourceImpl
import team.aliens.dms.kmp.core.database.datasource.notice.NoticeDatabaseDataSource
import team.aliens.dms.kmp.core.database.datasource.notice.NoticeDatabaseDataSourceImpl

internal val dataSourceModule =
    module {
        singleOf(::MealDatabaseDataSourceImpl) { bind<MealDatabaseDataSource>() }
        singleOf(::NoticeDatabaseDataSourceImpl) { bind<NoticeDatabaseDataSource>() }
    }
