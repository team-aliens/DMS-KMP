package team.aliens.dms.kmp.core.domain.usecase.remains.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.remains.GetAppliedRemainsOptionUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainsApplicationTimeUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainsOptionsUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.SetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.UpdateRemainsOptionUseCase

internal val remainsModule = module {
    singleOf(::GetAppliedRemainsOptionUseCase)
    singleOf(::GetRemainsApplicationTimeUseCase)
    singleOf(::GetRemainsOptionsUseCase)
    singleOf(::UpdateRemainsOptionUseCase)
    singleOf(::SetRemainUseCase)
    singleOf(::GetRemainUseCase)
}
