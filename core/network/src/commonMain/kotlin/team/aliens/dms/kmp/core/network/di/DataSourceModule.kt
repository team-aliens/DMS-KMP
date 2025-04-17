package team.aliens.dms.kmp.core.network.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.network.auth.datasource.KtorAuthDataSource
import team.aliens.dms.kmp.core.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.kmp.core.network.meal.datasource.KtorMealDataSource
import team.aliens.dms.kmp.core.network.meal.datasource.NetworkMealDataSource
import kotlin.math.sin

val networkDataSourceModule = module {
    single<NetworkAuthDataSource> { KtorAuthDataSource(get()) }
    single<NetworkMealDataSource> { KtorMealDataSource(get()) }
}
