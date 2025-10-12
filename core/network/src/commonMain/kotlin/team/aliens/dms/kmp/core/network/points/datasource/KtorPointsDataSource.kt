package team.aliens.dms.kmp.core.network.points.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import team.aliens.dms.kmp.core.network.points.model.request.GetPointsRequest
import team.aliens.dms.kmp.core.network.points.model.response.GetPointsResponse

internal class KtorPointsDataSource(
    private val client: HttpClient,
) : NetworkPointsDataSource {
    override suspend fun getPoints(request: GetPointsRequest): Result<GetPointsResponse> =
        kotlin.runCatching {
            client.get("/points") {
                parameter("type", request.query.type)
                parameter("page", request.query.page)
                parameter("size", request.query.size)
            }.body()
        }
}
