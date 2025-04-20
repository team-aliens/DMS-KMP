package team.aliens.dms.kmp.core.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository
import team.aliens.dms.kmp.core.data.auth.repository.AuthRepositoryImpl
import team.aliens.dms.kmp.core.data.meal.repository.MealRepository
import team.aliens.dms.kmp.core.data.meal.repository.MealRepositoryImpl
import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepository
import team.aliens.dms.kmp.core.data.notice.repository.NoticeRepositoryImpl

val repositoryModule = module {
    singleOf(::AuthRepositoryImpl) { bind<AuthRepository>() }
    singleOf(::MealRepositoryImpl) { bind<MealRepository>() }
    singleOf(::NoticeRepositoryImpl) { bind<NoticeRepository>() }
}
