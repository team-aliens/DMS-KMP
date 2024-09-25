package team.aliens.dms.kmp.core.datastore.jwt

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore
import team.aliens.dms.kmp.core.datastore.jwt.exception.AccessTokenExpirationNotFoundException
import team.aliens.dms.kmp.core.datastore.jwt.exception.AccessTokenNotFoundException
import team.aliens.dms.kmp.core.datastore.jwt.exception.CannotClearTokensException
import team.aliens.dms.kmp.core.datastore.jwt.exception.CannotStoreTokensException
import team.aliens.dms.kmp.core.datastore.jwt.exception.RefreshTokenExpirationNotFoundException
import team.aliens.dms.kmp.core.datastore.jwt.exception.RefreshTokenNotFoundException
import team.aliens.dms.kmp.core.datastore.jwt.model.AccessToken
import team.aliens.dms.kmp.core.datastore.jwt.model.RefreshToken
import team.aliens.dms.kmp.core.datastore.jwt.model.Tokens
import team.aliens.dms.kmp.core.datastore.util.transform

internal class JwtDataSourceImpl(
    private val jwtDataStore: PreferencesDataStore,
) : JwtDataSource() {

    override suspend fun loadTokens(): Tokens = runBlocking {
        jwtDataStore.data.map { preferences ->
            val accessTokenValue = preferences[ACCESS_TOKEN]
                ?: throw AccessTokenNotFoundException()
            val accessTokenExpiration = preferences[ACCESS_TOKEN_EXPIRATION]
                ?: throw AccessTokenExpirationNotFoundException()
            val refreshTokenValue = preferences[REFRESH_TOKEN]
                ?: throw RefreshTokenNotFoundException()
            val refreshTokenExpiration = preferences[REFRESH_TOKEN_EXPIRATION]
                ?: throw RefreshTokenExpirationNotFoundException()

            return@map Tokens(
                accessToken = AccessToken(
                    value = accessTokenValue,
                    expiration = Instant.fromEpochMilliseconds(accessTokenExpiration),
                ),
                refreshToken = RefreshToken(
                    value = refreshTokenValue,
                    expiration = Instant.fromEpochMilliseconds(refreshTokenExpiration),
                ),
            )
        }.first()
    }

    override suspend fun storeTokens(tokens: Tokens) {
        transform(onFailure = { throw CannotStoreTokensException() }) {
            jwtDataStore.edit { preferences ->
                val accessToken = tokens.accessToken
                val refreshToken = tokens.refreshToken
                preferences[ACCESS_TOKEN] = accessToken.value
                preferences[ACCESS_TOKEN_EXPIRATION] = accessToken.expiration.toEpochMilliseconds()
                preferences[REFRESH_TOKEN] = refreshToken.value
                preferences[REFRESH_TOKEN_EXPIRATION] =
                    refreshToken.expiration.toEpochMilliseconds()
            }
        }
    }

    override suspend fun clearTokens() {
        transform(
            onFailure = { throw CannotClearTokensException() },
        ) {
            jwtDataStore.edit { preferences -> preferences.clear() }
        }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access-token")
        val ACCESS_TOKEN_EXPIRATION = longPreferencesKey("access-token-expiration")
        val REFRESH_TOKEN = stringPreferencesKey("refresh-token")
        val REFRESH_TOKEN_EXPIRATION = longPreferencesKey("refresh-token-expiration")
    }
}
