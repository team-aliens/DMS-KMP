package team.aliens.dms.kmp.core.jwt.datastore

import team.aliens.dms.kmp.core.jwt.model.Tokens

abstract class JwtDataStoreDataSource {

    abstract suspend fun loadTokens(): Tokens

    abstract suspend fun storeTokens(tokens: Tokens)

    abstract suspend fun clearTokens()
}
