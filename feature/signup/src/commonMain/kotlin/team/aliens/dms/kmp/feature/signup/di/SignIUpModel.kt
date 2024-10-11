package team.aliens.dms.kmp.feature.signup.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeViewModel

val signUpModule = module {
    viewModelOf(::EnterSchoolVerificationCodeViewModel)
}
