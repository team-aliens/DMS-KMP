package team.aliens.dms.kmp.feature.signup.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.SetIdViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.SetPasswordViewModel
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsViewModel

val signUpModule = module {
    viewModelOf(::EnterSchoolVerificationCodeViewModel)
    viewModelOf(::EnterSchoolVerificationQuestionViewModel)
    viewModelOf(::EnterEmailViewModel)
    viewModelOf(::EnterEmailVerificationCodeViewModel)
    viewModelOf(::SetIdViewModel)
    viewModelOf(::SetPasswordViewModel)
    viewModelOf(::TermsViewModel)
}
