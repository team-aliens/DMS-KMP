package team.aliens.dms.kmp.core.data.auth.repository

import team.aliens.dms.kmp.core.model.auth.TokenModel
import team.aliens.dms.kmp.core.model.student.EmailModel
import team.aliens.dms.kmp.core.model.type.EmailVerificationType

interface AuthRepository {
    suspend fun signIn(
        accountId: String,
        password: String,
        deviceToken: String,
    ): Result<Unit>

    suspend fun sendEmailVerificationCode(
        email: String,
        type: EmailVerificationType,
    ): Result<Unit>

    suspend fun checkEmailVerificationCode(
        email: String,
        code: String,
        type: EmailVerificationType,
    ): Result<Unit>

    suspend fun checkIdExists(accountId: String): Result<EmailModel>

    suspend fun fetchTokens(): Result<TokenModel?>

    suspend fun updateTokens(token: TokenModel): Result<Unit>

    suspend fun clearTokens(): Result<Unit>

    suspend fun reissueToken(refreshToken: String): Result<Unit>
}
