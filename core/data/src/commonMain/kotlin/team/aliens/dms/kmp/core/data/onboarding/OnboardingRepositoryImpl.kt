package team.aliens.dms.kmp.core.data.onboarding

import team.aliens.dms.kmp.core.datastore.onboarding.OnboardingPreferencesDataSource

internal class OnboardingRepositoryImpl(
    private val onboardingPreferencesDataSource: OnboardingPreferencesDataSource,
) : OnboardingRepository {
    override suspend fun getOnboardingCompleted(): Result<Boolean> = onboardingPreferencesDataSource.getOnboardingCompleted()

    override suspend fun setOnboardingCompleted(isCompleted: Boolean): Result<Unit> =
        onboardingPreferencesDataSource.setOnboardingCompleted(isCompleted = isCompleted)
}
