package team.aliens.dms.kmp.feature.meal.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.meal.viewmodel.MealViewModel

val mealModule = module {
    viewModelOf(::MealViewModel)
}
