package team.aliens.dms.kmp.feature.home.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.home.viewmodel.HomeViewModel

val homeModule = module {
    viewModelOf(::HomeViewModel)
}
