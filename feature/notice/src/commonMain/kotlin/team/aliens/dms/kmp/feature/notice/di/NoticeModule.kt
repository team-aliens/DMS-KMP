package team.aliens.dms.kmp.feature.notice.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticeDetailsViewModel
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesViewModel

val noticeModule = module {
    viewModelOf(::NoticesViewModel)
    viewModelOf(::NoticeDetailsViewModel)
}
