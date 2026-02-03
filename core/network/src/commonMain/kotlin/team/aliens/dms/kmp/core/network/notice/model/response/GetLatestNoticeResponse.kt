package team.aliens.dms.kmp.core.network.notice.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetLatestNoticeResponse(
    val id: String,
    val title: String,
)
