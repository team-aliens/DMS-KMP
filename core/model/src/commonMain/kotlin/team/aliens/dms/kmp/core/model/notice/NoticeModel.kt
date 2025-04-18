package team.aliens.dms.kmp.core.model.notice

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NoticeModel(
    val id: String,
    val title: String,
    val createdAt: LocalDateTime,
)
