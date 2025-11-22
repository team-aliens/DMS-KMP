package team.aliens.dms.kmp.core.datastore.onboarding

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore

internal class OnboardingPreferencesDataSourceImpl(
    private val preferencesDataStore: PreferencesDataStore,
) : OnboardingPreferencesDataSource {
    override suspend fun setOnboardingCompleted(isCompleted: Boolean): Result<Unit> =
        kotlin.runCatching {
            preferencesDataStore.edit { preferences ->
                preferences[ONBOARDING_COMPLETED] = isCompleted
            }
        }

    override suspend fun getOnboardingCompleted(): Result<Boolean> = kotlin.runCatching {
        preferencesDataStore.data.firstOrNull()?.get(ONBOARDING_COMPLETED) ?: false
    }

    private companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }
}
