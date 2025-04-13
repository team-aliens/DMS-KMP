package team.aliens.dms.kmp.core.domain.usecase.meal.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.meal.GetMealUseCase

internal val mealModule = module {
    singleOf(::GetMealUseCase)
}
