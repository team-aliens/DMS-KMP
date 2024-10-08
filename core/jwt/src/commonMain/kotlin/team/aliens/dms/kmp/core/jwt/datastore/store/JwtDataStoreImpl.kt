package team.aliens.dms.kmp.core.jwt.datastore.store

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore
import team.aliens.dms.kmp.core.datastore.util.getValueOrThrow
import team.aliens.dms.kmp.core.datastore.util.transform
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.AccessTokenExpirationNotFoundException
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.AccessTokenNotFoundException
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.CannotClearTokensException
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.CannotStoreTokensException
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.RefreshTokenExpirationNotFoundException
import team.aliens.dms.kmp.core.jwt.datastore.store.exception.RefreshTokenNotFoundException
import team.aliens.dms.kmp.core.jwt.model.AccessToken
import team.aliens.dms.kmp.core.jwt.model.RefreshToken
import team.aliens.dms.kmp.core.jwt.model.Tokens

internal class JwtDataStoreImpl(
    private val jwtDataStore: PreferencesDataStore,
) : JwtDataStore() {

    override suspend fun loadTokens(): Tokens {
        return jwtDataStore.data.map { preferences ->
            val accessTokenValue =
                preferences.getValueOrThrow(
                    ACCESS_TOKEN,
                    AccessTokenNotFoundException(),
                )
            val accessTokenExpiration = preferences.getValueOrThrow(
                ACCESS_TOKEN_EXPIRATION,
                AccessTokenExpirationNotFoundException(),
            )
            val refreshTokenValue =
                preferences.getValueOrThrow(
                    REFRESH_TOKEN,
                    RefreshTokenNotFoundException(),
                )
            val refreshTokenExpiration = preferences.getValueOrThrow(
                REFRESH_TOKEN_EXPIRATION,
                RefreshTokenExpirationNotFoundException(),
            )

            Tokens(
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
