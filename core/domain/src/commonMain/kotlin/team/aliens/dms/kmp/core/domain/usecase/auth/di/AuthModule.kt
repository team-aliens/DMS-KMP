package team.aliens.dms.kmp.core.domain.usecase.auth.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase

internal val authModule = module {
    singleOf(::GetTokenUseCase)
}
