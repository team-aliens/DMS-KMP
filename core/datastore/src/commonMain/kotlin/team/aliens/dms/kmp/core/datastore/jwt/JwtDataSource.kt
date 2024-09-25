package team.aliens.dms.kmp.core.datastore.jwt

import team.aliens.dms.kmp.core.datastore.jwt.model.Tokens

abstract class JwtDataSource {

    abstract fun loadTokens(): Tokens

    abstract suspend fun storeTokens(tokens: Tokens)

    abstract suspend fun clearTokens()
}
