package team.aliens.dms.kmp.core.datastore.onboarding

interface OnboardingPreferencesDataSource {

    suspend fun setOnboardingCompleted(isCompleted: Boolean): Result<Unit>

    suspend fun getOnboardingCompleted(): Result<Boolean>
}
