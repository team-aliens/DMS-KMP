package team.aliens.dms.kmp.core.domain.usecase.onboarding

import team.aliens.dms.kmp.core.data.onboarding.OnboardingRepository

class GetOnboardingCompletedUseCase(
    private val onboardingRepository: OnboardingRepository,
) {
    suspend operator fun invoke() = onboardingRepository.getOnboardingCompleted()
}
