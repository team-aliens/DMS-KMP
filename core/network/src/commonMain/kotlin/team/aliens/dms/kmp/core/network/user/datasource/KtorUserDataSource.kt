package team.aliens.dms.kmp.core.network.user.datasource

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import team.aliens.dms.kmp.core.network.user.model.EditPasswordRequest

internal class KtorUserDataSource(
    private val client: HttpClient,
) : NetworkUserDataSource {
    override suspend fun editPassword(request: EditPasswordRequest): Result<Unit> =
        runCatching {
            client.patch("/users/password") {
                setBody(request.body)
            }
        }

    override suspend fun comparePassword(password: String): Result<Unit> =
        runCatching {
            client.get("/users/password") {
                parameter("password", password)
            }
        }
}