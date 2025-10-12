package team.aliens.dms.kmp.core.network.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.network.auth.datasource.KtorAuthDataSource
import team.aliens.dms.kmp.core.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.kmp.core.network.meal.datasource.KtorMealDataSource
import team.aliens.dms.kmp.core.network.meal.datasource.NetworkMealDataSource
import team.aliens.dms.kmp.core.network.notice.datasource.KtorNoticeDataSource
import team.aliens.dms.kmp.core.network.notice.datasource.NetworkNoticeDataSource
import team.aliens.dms.kmp.core.network.points.datasource.KtorPointsDataSource
import team.aliens.dms.kmp.core.network.points.datasource.NetworkPointsDataSource
import team.aliens.dms.kmp.core.network.remains.datasource.KtorRemainsDataSource
import team.aliens.dms.kmp.core.network.remains.datasource.NetworkRemainsDataSource
import team.aliens.dms.kmp.core.network.student.datasource.KtorStudentDataSource
import team.aliens.dms.kmp.core.network.student.datasource.NetworkStudentDataSource
import team.aliens.dms.kmp.core.network.votes.datasource.KtorVotesDataSource
import team.aliens.dms.kmp.core.network.votes.datasource.NetworkVotesDataSource

val networkDataSourceModule = module {
    single<NetworkAuthDataSource> { KtorAuthDataSource(get()) }
    single<NetworkMealDataSource> { KtorMealDataSource(get()) }
    single<NetworkNoticeDataSource> { KtorNoticeDataSource(get()) }
    single<NetworkStudentDataSource> { KtorStudentDataSource(get()) }
    single<NetworkRemainsDataSource> { KtorRemainsDataSource(get()) }
    single<NetworkVotesDataSource> { KtorVotesDataSource(get()) }
    single<NetworkPointsDataSource> { KtorPointsDataSource(get()) }
}
