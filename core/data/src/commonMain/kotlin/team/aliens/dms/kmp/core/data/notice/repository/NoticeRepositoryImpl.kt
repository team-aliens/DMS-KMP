package team.aliens.dms.kmp.core.data.notice.repository

import team.aliens.dms.kmp.core.data.notice.mapper.toDto
import team.aliens.dms.kmp.core.data.notice.mapper.toModel
import team.aliens.dms.kmp.core.model.notice.NoticeDetailModel
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.core.model.notice.NoticeStatusModel
import team.aliens.dms.kmp.core.model.type.OrderType
import team.aliens.dms.kmp.core.network.notice.datasource.NetworkNoticeDataSource
import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticeDetailRequest
import team.aliens.dms.kmp.core.network.notice.model.request.GetNoticesRequest

internal class NoticeRepositoryImpl(
    private val networkNoticeDataSource: NetworkNoticeDataSource,
) : NoticeRepository {
    override suspend fun getWhetherNewNoticesExist(): Result<NoticeStatusModel> =
        networkNoticeDataSource.getWhetherNewNoticesExist().map { it.toModel() }

    override suspend fun getNoticeDetail(noticeId: String): Result<NoticeDetailModel> =
        networkNoticeDataSource.getNoticeDetail(
            request = GetNoticeDetailRequest(
                path = GetNoticeDetailRequest.Path(
                    noticeId = noticeId,
                ),
            ),
        ).map { it.toModel() }

    override suspend fun getNotices(orderType: OrderType): Result<List<NoticeModel>> =
        networkNoticeDataSource.getNotices(
            request = GetNoticesRequest(
                query = GetNoticesRequest.Query(
                    order = orderType.toDto(),
                ),
            ),
        ).map { it.toModel() }
}
