package team.aliens.dms.kmp.core.datastore.auth

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.datastore.PreferencesDataStore
import team.aliens.dms.kmp.core.datastore.auth.exception.CannotClearTokensException
import team.aliens.dms.kmp.core.datastore.auth.exception.CannotStoreTokensException
import team.aliens.dms.kmp.core.datastore.util.transform
import team.aliens.dms.kmp.core.model.auth.AccessToken
import team.aliens.dms.kmp.core.model.auth.RefreshToken
import team.aliens.dms.kmp.core.model.auth.TokenModel

internal class AuthPreferencesDataSourceImpl(
    private val jwtDataStore: PreferencesDataStore,
) : AuthPreferencesDataSource {

    override suspend fun loadTokens(): Result<TokenModel?> = kotlin.runCatching {
        jwtDataStore.data.firstOrNull()?.let { preferences ->
            val accessTokenValue = preferences[ACCESS_TOKEN] ?: return@let null
            val accessTokenExpiration = preferences[ACCESS_TOKEN_EXPIRATION] ?: return@let null
            val refreshTokenValue = preferences[REFRESH_TOKEN] ?: return@let null
            val refreshTokenExpiration = preferences[REFRESH_TOKEN_EXPIRATION] ?: return@let null

            TokenModel(
                accessToken = AccessToken(
                    value = accessTokenValue,
                    expiration = LocalDateTime.parse(accessTokenExpiration),
                ),
                refreshToken = RefreshToken(
                    value = refreshTokenValue,
                    expiration = LocalDateTime.parse(refreshTokenExpiration),
                ),
            )
        }
    }

    override suspend fun storeTokens(token: TokenModel): Result<Unit> = runCatching {
        transform(onFailure = { throw CannotStoreTokensException() }) {
            jwtDataStore.edit { preferences ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                preferences[ACCESS_TOKEN] = accessToken.value
                preferences[ACCESS_TOKEN_EXPIRATION] = accessToken.expiration.toString()
                preferences[REFRESH_TOKEN] = refreshToken.value
                preferences[REFRESH_TOKEN_EXPIRATION] = refreshToken.expiration.toString()
            }
        }
    }

    override suspend fun clearTokens(): Result<Unit> = kotlin.runCatching {
        transform(
            onFailure = { throw CannotClearTokensException() },
        ) {
            jwtDataStore.edit { preferences -> preferences.clear() }
        }
    }

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access-token")
        val ACCESS_TOKEN_EXPIRATION = stringPreferencesKey("access-token-expiration")
        val REFRESH_TOKEN = stringPreferencesKey("refresh-token")
        val REFRESH_TOKEN_EXPIRATION = stringPreferencesKey("refresh-token-expiration")
    }
}
