package team.aliens.dms.kmp.feature.application.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.application.viewmodel.ApplicationViewModel

val applicationModule = module {
    viewModelOf(::ApplicationViewModel)
}
