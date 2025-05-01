package team.aliens.dms.kmp.feature.mypage.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.mypage.viewmodel.MyPageViewModel

val myPageModule = module {
    viewModelOf(::MyPageViewModel)
}
