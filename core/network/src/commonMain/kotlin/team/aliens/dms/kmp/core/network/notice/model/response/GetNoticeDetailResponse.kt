package team.aliens.dms.kmp.core.network.notice.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GetNoticeDetailResponse(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: String,
)
