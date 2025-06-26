package team.aliens.dms.kmp.core.datastore.auth

import team.aliens.dms.kmp.core.model.auth.TokenModel

interface AuthPreferencesDataSource {

    suspend fun loadTokens(): Result<TokenModel?>

    suspend fun storeTokens(token: TokenModel): Result<Unit>

    suspend fun clearTokens(): Result<Unit>
}
