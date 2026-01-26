package team.aliens.dms.kmp.core.model.notification

import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.model.type.NotificationType
import team.aliens.dms.kmp.core.model.type.PointType

@Serializable
data class NotificationsModel(
    val notifications: List<NotificationModel>,
) {
    @Serializable
    data class NotificationModel(
        val id: String,
        val topic: NotificationType,
        val pointDetailTopic: PointType?,
        val linkId: String,
        val title: String,
        val content: String,
        val createdAt: String,
        val isRead: Boolean,
    )
}
