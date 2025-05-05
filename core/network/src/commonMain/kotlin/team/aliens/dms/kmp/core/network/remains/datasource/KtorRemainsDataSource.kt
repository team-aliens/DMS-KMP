package team.aliens.dms.kmp.core.network.remains.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import team.aliens.dms.kmp.core.network.remains.model.request.UpdateRemainsOptionRequest
import team.aliens.dms.kmp.core.network.remains.model.response.GetAppliedRemainsOptionResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsApplicationTimeResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsOptionsResponse

internal class KtorRemainsDataSource(private val client: HttpClient):  NetworkRemainsDataSource{
    override suspend fun updateRemainsOption(request: UpdateRemainsOptionRequest): Result<Unit> = kotlin.runCatching {
        client.put("/remains/${request.path.remainOptionId}").body()
    }

    override suspend fun getAppliedRemainsOption(): Result<GetAppliedRemainsOptionResponse> = kotlin.runCatching {
        client.get("/remains/my").body()
    }

    override suspend fun getRemainsApplicationTime(): Result<GetRemainsApplicationTimeResponse> = kotlin.runCatching {
        client.get("/remains/available-time").body()
    }

    override suspend fun getRemainsOptions(): Result<GetRemainsOptionsResponse> = kotlin.runCatching {
        client.get("/remains/options").body()
    }
}
