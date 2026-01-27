package team.aliens.dms.kmp.core.domain.usecase.user.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.user.ComparePasswordUseCase
import team.aliens.dms.kmp.core.domain.usecase.user.EditPasswordUseCase

internal val userModule = module {
    singleOf(::EditPasswordUseCase)
    singleOf(::ComparePasswordUseCase)
}