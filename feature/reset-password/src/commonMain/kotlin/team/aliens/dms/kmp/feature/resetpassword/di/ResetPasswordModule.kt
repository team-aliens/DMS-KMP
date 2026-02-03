package team.aliens.dms.kmp.feature.resetpassword.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.resetpassword.ResetPasswordViewModel

val resetPasswordModule = module {
    viewModelOf(::ResetPasswordViewModel)
}
