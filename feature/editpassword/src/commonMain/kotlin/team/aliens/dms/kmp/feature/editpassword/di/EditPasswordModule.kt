package team.aliens.dms.kmp.feature.editpassword.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.editpassword.viewmodel.CheckPasswordViewModel
import team.aliens.dms.kmp.feature.editpassword.viewmodel.EditPasswordViewModel

val editPasswordModule = module {
    viewModelOf(::CheckPasswordViewModel)
    viewModelOf(::EditPasswordViewModel)
}
