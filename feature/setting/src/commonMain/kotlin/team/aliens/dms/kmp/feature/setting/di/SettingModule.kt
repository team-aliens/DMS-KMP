package team.aliens.dms.kmp.feature.setting.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.setting.viewmodel.SettingViewModel

val settingModule = module {
    viewModelOf(::SettingViewModel)
}
