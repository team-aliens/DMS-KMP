package team.aliens.dms.kmp.core.network.notification.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchNotificationsResponse(
    @SerialName("notifications") val notifications: List<NotificationResponse>,
) {
    @Serializable
    data class NotificationResponse(
        @SerialName("id") val id: String,
        @SerialName("topic") val topic: String,
        @SerialName("point_detail_topic") val pointDetailTopic: String?,
        @SerialName("link_identifier") val linkId: String,
        @SerialName("title") val title: String,
        @SerialName("content") val content: String,
        @SerialName("created_at") val createdAt: String,
        @SerialName("is_read") val isRead: Boolean,
    )
}
