package team.aliens.dms.kmp.core.data.auth.repository

import team.aliens.dms.kmp.core.data.auth.model.EmailVerificationType
import team.aliens.dms.kmp.core.datastore.auth.model.Tokens
import team.aliens.dms.kmp.core.model.auth.EmailModel

interface AuthRepository {
    suspend fun signIn(accountId: String, password: String, deviceToken: String): Result<Unit>
    suspend fun sendEmailVerificationCode(email: String, type: EmailVerificationType): Result<Unit>
    suspend fun checkEmailVerificationCode(email: String, code: String, type: EmailVerificationType): Result<Unit>
    suspend fun checkIdExists(accountId: String): Result<EmailModel>
    suspend fun fetchTokens(): Result<Tokens?>
    suspend fun updateTokens(tokens: Tokens): Result<Unit>
    suspend fun clearTokens(): Result<Unit>
}
