package team.aliens.dms.kmp.core.domain.usecase.onboarding.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.onboarding.GetOnboardingCompletedUseCase
import team.aliens.dms.kmp.core.domain.usecase.onboarding.SetOnboardingCompletedUseCase

internal val onboardingModule = module {
    singleOf(::GetOnboardingCompletedUseCase)
    singleOf(::SetOnboardingCompletedUseCase)
}
