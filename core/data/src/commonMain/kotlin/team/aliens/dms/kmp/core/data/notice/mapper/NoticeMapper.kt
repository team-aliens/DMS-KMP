package team.aliens.dms.kmp.core.data.notice.mapper

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.model.notice.NoticeDetailModel
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.core.model.notice.NoticeStatusModel
import team.aliens.dms.kmp.core.model.type.OrderType
import team.aliens.dms.kmp.core.network.notice.model.dto.OrderTypeDto
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticeDetailResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetNoticesResponse
import team.aliens.dms.kmp.core.network.notice.model.response.GetWhetherNewNoticesExistResponse

internal fun GetWhetherNewNoticesExistResponse.toModel() = NoticeStatusModel(
    whetherNewNotices = this.whetherNewNotices,
)

internal fun GetNoticeDetailResponse.toModel() = NoticeDetailModel(
    id = this.id,
    title = this.title,
    content = this.content,
    createdAt = LocalDateTime.parse(this.createdAt),
)

internal fun OrderType.toDto() = when(this) {
    OrderType.OLD -> OrderTypeDto.OLD
    OrderType.NEW -> OrderTypeDto.NEW
}

internal fun GetNoticesResponse.toModel() = this.notices.map { it.toModel() }

private fun GetNoticesResponse.Notice.toModel() = NoticeModel(
    id = this.id,
    title = this.title,
    createdAt = LocalDateTime.parse(this.createdAt),
)
