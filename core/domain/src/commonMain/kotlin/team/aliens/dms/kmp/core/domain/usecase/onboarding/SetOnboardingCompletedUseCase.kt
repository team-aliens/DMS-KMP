package team.aliens.dms.kmp.core.domain.usecase.onboarding

import team.aliens.dms.kmp.core.data.onboarding.OnboardingRepository

class SetOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository,
) {
    suspend operator fun invoke(isCompleted: Boolean) =
        onboardingRepository.setOnboardingCompleted(isCompleted = isCompleted)
}
