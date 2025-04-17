package team.aliens.dms.kmp.core.database.di

import app.cash.sqldelight.db.SqlDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.database.DmsDatabase
import team.aliens.dms.kmp.core.database.DriverFactory
import team.aliens.dms.kmp.core.database.MealEntity
import team.aliens.dms.kmp.core.database.adapter.dateAdapter
import team.aliens.dms.kmp.core.database.adapter.mealAdapter
import team.aliens.dms.kmp.core.database.dao.MealDao

val databaseModule =
    module {
        includes(platformDatabaseModule, dataSourceModule)
        single { MealDao(get()) }
        single<SqlDriver> { get<DriverFactory>().createDriver() }
        single {
            DmsDatabase(
                driver = get(),
                MealEntityAdapter = MealEntity.Adapter(
                    dateAdapter = dateAdapter,
                    breakfastAdapter = mealAdapter,
                    lunchAdapter = mealAdapter,
                    dinnerAdapter = mealAdapter,
                ),
            )
        }
    }

internal expect val platformDatabaseModule: Module
