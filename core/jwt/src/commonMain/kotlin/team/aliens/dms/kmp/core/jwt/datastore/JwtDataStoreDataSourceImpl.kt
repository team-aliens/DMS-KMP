package team.aliens.dms.kmp.core.jwt.datastore

import team.aliens.dms.kmp.core.jwt.datastore.store.JwtDataStore
import team.aliens.dms.kmp.core.jwt.model.Tokens

internal class JwtDataStoreDataSourceImpl(
    private val jwtDataStore: JwtDataStore,
) : JwtDataStoreDataSource() {

    override suspend fun loadTokens(): Tokens = jwtDataStore.loadTokens()

    override suspend fun storeTokens(tokens: Tokens) = jwtDataStore.storeTokens(tokens)

    override suspend fun clearTokens() = jwtDataStore.clearTokens()
}
