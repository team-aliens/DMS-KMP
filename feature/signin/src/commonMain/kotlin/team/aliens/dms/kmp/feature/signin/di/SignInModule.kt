package team.aliens.dms.kmp.feature.signin.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInViewModel

val signInModule = module {
    viewModelOf(::SignInViewModel)
}
