package team.aliens.dms.kmp.core.datastore.auth

import team.aliens.dms.kmp.core.datastore.auth.model.Tokens

interface AuthPreferencesDataSource {

    suspend fun loadTokens(): Result<Tokens?>

    suspend fun storeTokens(tokens: Tokens): Result<Unit>

    suspend fun clearTokens(): Result<Unit>
}
