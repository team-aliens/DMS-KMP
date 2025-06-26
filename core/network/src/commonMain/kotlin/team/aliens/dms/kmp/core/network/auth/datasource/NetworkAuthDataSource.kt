package team.aliens.dms.kmp.core.network.auth.datasource

import team.aliens.dms.kmp.core.network.auth.model.dto.TokenDto
import team.aliens.dms.kmp.core.network.auth.model.request.CheckEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.CheckIdExistsRequest
import team.aliens.dms.kmp.core.network.auth.model.request.ReissueRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SendEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SignInRequest
import team.aliens.dms.kmp.core.network.auth.model.response.CheckIdExistsResponse

interface NetworkAuthDataSource {
    suspend fun signIn(request: SignInRequest): Result<TokenDto>
    suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest): Result<Unit>
    suspend fun checkEmailVerificationCode(request: CheckEmailVerificationCodeRequest): Result<Unit>
    suspend fun checkIdExists(request: CheckIdExistsRequest): Result<CheckIdExistsResponse>
    suspend fun reissueToken(request: ReissueRequest): Result<TokenDto>
}
