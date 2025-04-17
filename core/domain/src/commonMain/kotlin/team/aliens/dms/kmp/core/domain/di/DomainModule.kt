package team.aliens.dms.kmp.core.domain.di

import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.auth.di.authModule
import team.aliens.dms.kmp.core.domain.usecase.meal.di.mealModule

val domainModule = module {
    includes(
        authModule,
        mealModule,
    )
}
