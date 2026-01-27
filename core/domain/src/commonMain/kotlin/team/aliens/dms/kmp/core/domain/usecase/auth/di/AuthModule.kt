package team.aliens.dms.kmp.core.domain.usecase.auth.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckIdExistsUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.ReissueTokenUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SignInUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SignOutUseCase

internal val authModule = module {
    singleOf(::GetTokenUseCase)
    singleOf(::SignInUseCase)
    singleOf(::SignOutUseCase)
    singleOf(::ReissueTokenUseCase)
    singleOf(::SendEmailVerificationCodeUseCase)
    singleOf(::CheckEmailVerificationCodeUseCase)
    singleOf(::CheckIdExistsUseCase)
}
