package team.aliens.dms.kmp.core.model.notice

import kotlinx.serialization.Serializable

@Serializable
data class NoticeStatusModel(
    val whetherNewNotices: Boolean,
)
