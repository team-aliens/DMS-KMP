package team.aliens.dms.kmp.core.network.notice.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticeDetailRequest
import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticesRequest
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticeDetailResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticesResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetWhetherNewNoticesExistResponse

internal class KtorNoticeDataSource(private val client: HttpClient): NetworkNoticeDataSource {
    override suspend fun getWhetherNewNoticesExist(): Result<GetWhetherNewNoticesExistResponse> = runCatching {
        client.get("/notices/status").body()
    }

    override suspend fun getNoticeDetail(request: GetNoticeDetailRequest): Result<GetNoticeDetailResponse> = kotlin.runCatching {
        client.get("/notices/${request.path.noticeId}").body()
    }

    override suspend fun getNotices(request: GetNoticesRequest): Result<GetNoticesResponse> = kotlin.runCatching {
        client.get("/notices"){
            parameter("order",request.query.order)
        }.body()
    }
}
