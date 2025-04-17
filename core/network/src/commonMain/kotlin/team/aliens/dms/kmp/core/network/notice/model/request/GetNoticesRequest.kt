package team.aliens.dms.kmp.core.network.notice.model.request

import team.aliens.dms.kmp.core.network.notice.model.dto.OrderTypeDto

data class GetNoticesRequest(
    val query: Query,
) {
    data class Query(
        val order: OrderTypeDto,
    )
}
