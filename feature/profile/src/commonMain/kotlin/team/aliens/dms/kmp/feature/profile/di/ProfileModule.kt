package team.aliens.dms.kmp.feature.profile.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.profile.viewmodel.AdjustProfileViewModel
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileViewModel

val profileModule = module {
    viewModelOf(::SelectProfileViewModel)
    viewModelOf(::AdjustProfileViewModel)
}
