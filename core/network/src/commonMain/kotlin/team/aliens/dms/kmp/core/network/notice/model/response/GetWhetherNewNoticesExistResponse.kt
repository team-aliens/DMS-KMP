package team.aliens.dms.kmp.core.network.notice.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetWhetherNewNoticesExistResponse(
    val whetherNewNotices: Boolean,
)
