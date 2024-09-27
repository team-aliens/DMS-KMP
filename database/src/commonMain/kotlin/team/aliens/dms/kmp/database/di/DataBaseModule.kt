package team.aliens.dms.kmp.database.di

import org.koin.dsl.module
import team.aliens.dms.kmp.database.meal.DatabaseMealDataSource
import team.aliens.dms.kmp.database.meal.DatabaseMealDataSourceImpl
import team.aliens.dms.kmp.database.notice.DatabaseNoticeDataSource
import team.aliens.dms.kmp.database.notice.DatabaseNoticeDataSourceImpl

val databaseModule =
    module {
        single<DatabaseMealDataSource> {
            DatabaseMealDataSourceImpl(get())
        }

        single<DatabaseNoticeDataSource> {
            DatabaseNoticeDataSourceImpl(get())
        }
    }
