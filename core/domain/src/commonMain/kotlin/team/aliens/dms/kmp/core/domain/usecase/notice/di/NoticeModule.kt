package team.aliens.dms.kmp.core.domain.usecase.notice.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticeDetailUseCase
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticesUseCase
import team.aliens.dms.kmp.core.domain.usecase.notice.GetWhetherNewNoticesExistUseCase

internal val noticeModule = module {
    singleOf(::GetNoticesUseCase)
    singleOf(::GetNoticeDetailUseCase)
    singleOf(::GetWhetherNewNoticesExistUseCase)
}
