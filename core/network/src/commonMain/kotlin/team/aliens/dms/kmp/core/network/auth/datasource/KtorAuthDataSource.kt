package team.aliens.dms.kmp.core.network.auth.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import team.aliens.dms.kmp.core.network.auth.model.request.CheckEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.CheckIdExistsRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SendEmailVerificationCodeRequest
import team.aliens.dms.kmp.core.network.auth.model.request.SignInRequest
import team.aliens.dms.kmp.core.network.auth.model.response.CheckIdExistsResponse
import team.aliens.dms.kmp.core.network.auth.model.response.SignInResponse

internal class KtorAuthDataSource(private val client: HttpClient) : NetworkAuthDataSource {
    override suspend fun signIn(request: SignInRequest): Result<SignInResponse> = runCatching {
        client.post("/auth/tokens") {
            setBody(request.body)
        }.body()
    }


    override suspend fun sendEmailVerificationCode(request: SendEmailVerificationCodeRequest): Result<Unit> =
        runCatching {
            client.post("/auth/code") {
                setBody(request.body)
            }.body()
        }

    override suspend fun checkEmailVerificationCode(request: CheckEmailVerificationCodeRequest): Result<Unit> =
        runCatching {
            client.get("/auth/code") {
                parameter("email", request.query.email)
                parameter("auth_code", request.query.authCode)
                parameter("type", request.query.type)
            }.body()
        }

    override suspend fun checkIdExists(request: CheckIdExistsRequest): Result<CheckIdExistsResponse> =
        runCatching {
            client.get("/auth/account-id") {
                parameter("account_id", request.query)
            }.body()
        }
}
