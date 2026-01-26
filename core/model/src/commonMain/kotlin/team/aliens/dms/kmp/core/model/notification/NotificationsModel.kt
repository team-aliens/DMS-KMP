package team.aliens.dms.kmp.core.model.notification

import kotlinx.serialization.Serializable

@Serializable
data class NotificationsModel(
    val notifications: List<NotificationModel>,
) {
    @Serializable
    data class NotificationModel(
        val id: String,
        val topic: String,
        val pointDetailTopic: String?,
        val linkId: String,
        val title: String,
        val content: String,
        val createdAt: String,
        val isRead: Boolean,
    )
}
