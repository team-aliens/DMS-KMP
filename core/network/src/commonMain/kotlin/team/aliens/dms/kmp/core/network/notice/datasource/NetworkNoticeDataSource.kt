package team.aliens.dms.kmp.core.network.notice.datasource

import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticeDetailRequest
import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticesRequest
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticeDetailResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticesResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetWhetherNewNoticesExistResponse

interface NetworkNoticeDataSource {
    suspend fun getWhetherNewNoticesExist(): Result<GetWhetherNewNoticesExistResponse>
    suspend fun getNoticeDetail(request: GetNoticeDetailRequest): Result<GetNoticeDetailResponse>
    suspend fun getNotices(request: GetNoticesRequest): Result<GetNoticesResponse>
}
