package team.aliens.dms.kmp.core.data.notice.repository

import team.aliens.dms.kmp.core.model.notice.LatestNoticeModel
import team.aliens.dms.kmp.core.model.notice.NoticeDetailModel
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.core.model.notice.NoticeStatusModel
import team.aliens.dms.kmp.core.model.type.OrderType

interface NoticeRepository {
    suspend fun getWhetherNewNoticesExist(): Result<NoticeStatusModel>

    suspend fun getNoticeDetail(noticeId: String): Result<NoticeDetailModel>

    suspend fun getNotices(orderType: OrderType): Result<List<NoticeModel>>

    suspend fun getLatestNotice(): Result<LatestNoticeModel>
}
