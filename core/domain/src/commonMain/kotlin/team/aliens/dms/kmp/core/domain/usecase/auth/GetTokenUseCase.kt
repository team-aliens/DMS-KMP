package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource

class GetTokenUseCase(
    private val authPreferencesDataSource: AuthPreferencesDataSource,
) {
    suspend operator fun invoke() = authPreferencesDataSource.loadTokens()

}
