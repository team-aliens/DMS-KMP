package team.aliens.dms.kmp.core.data.auth.repository

import team.aliens.dms.kmp.core.data.auth.mapper.toModel
import team.aliens.dms.kmp.core.data.auth.model.EmailVerificationType
import team.aliens.dms.kmp.core.datastore.auth.AuthPreferencesDataSource
import team.aliens.dms.kmp.core.model.auth.EmailModel
import team.aliens.dms.kmp.core.model.auth.TokenModel
import team.aliens.dms.kmp.core.network.auth.datasource.NetworkAuthDataSource
import team.aliens.dms.kmp.core.network.auth.model.request.CheckEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.CheckIdExistsRequest
import team.aliens.dms.kmp.core.network.auth.model.request.ReissueRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SendEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SignInRequest

internal class AuthRepositoryImpl(
    private val networkAuthDatasource: NetworkAuthDataSource,
    private val authPreferencesDataSource: AuthPreferencesDataSource,
) : AuthRepository {
    override suspend fun signIn(
        accountId: String,
        password: String,
        deviceToken: String,
    ): Result<Unit> {
        val response = networkAuthDatasource.signIn(
            request = SignInRequest(
                body = SignInRequest.Body(
                    accountId = accountId,
                    password = password,
                    deviceToken = deviceToken,
                ),
            ),
        )
        response.getOrNull()?.let { token ->
            authPreferencesDataSource.storeTokens(
                token = token.toModel(),
            )
        }
        return response.map { }
    }

    override suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ): Result<Unit> =
        networkAuthDatasource.sendEmailVerificationCode(
            request = SendEmailVerificationCodeRequest(
                body = SendEmailVerificationCodeRequest.Body(
                    email = email,
                    type = type.name,
                ),
            ),
        )

    override suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ): Result<Unit> =
        networkAuthDatasource.checkEmailVerificationCode(
            request = CheckEmailVerificationCodeRequest(
                query = CheckEmailVerificationCodeRequest.Query(
                    email = email,
                    authCode = code,
                    type = type.name,
                ),
            ),
        )

    override suspend fun checkIdExists(accountId: String): Result<EmailModel> =
        networkAuthDatasource.checkIdExists(
            request = CheckIdExistsRequest(
                query = CheckIdExistsRequest.Query(
                    accountId = accountId,
                ),
            ),
        ).map { it.toModel() }

    override suspend fun fetchTokens(): Result<TokenModel?> = authPreferencesDataSource.loadTokens()

    override suspend fun updateTokens(token: TokenModel): Result<Unit> =
        authPreferencesDataSource.storeTokens(token = token)

    override suspend fun clearTokens(): Result<Unit> = authPreferencesDataSource.clearTokens()
    override suspend fun reissueToken(refreshToken: String): Result<Unit> {
        val response = networkAuthDatasource.reissueToken(
            request = ReissueRequest(header = ReissueRequest.Header(refreshToken = refreshToken)),
        )
        response.getOrNull()?.let { token ->
            authPreferencesDataSource.storeTokens(
                token = token.toModel(),
            )
        }
        return response.map { }
    }
}
