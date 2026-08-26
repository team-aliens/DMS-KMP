package team.aliens.dms.kmp.core.data.onboarding

interface OnboardingRepository {
    suspend fun getOnboardingCompleted(): Result<Boolean>

    suspend fun setOnboardingCompleted(isCompleted: Boolean): Result<Unit>
}
