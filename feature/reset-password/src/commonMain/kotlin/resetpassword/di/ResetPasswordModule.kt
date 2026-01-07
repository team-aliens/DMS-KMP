package resetpassword.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import resetpassword.ResetPasswordViewModel

val resetPasswordModule = module {
    viewModelOf(::ResetPasswordViewModel)
}
