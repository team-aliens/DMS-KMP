package team.aliens.dms.kmp.feature.splash.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.splash.viewmodel.SplashViewModel

val splashModule = module {
    viewModelOf(::SplashViewModel)
}
