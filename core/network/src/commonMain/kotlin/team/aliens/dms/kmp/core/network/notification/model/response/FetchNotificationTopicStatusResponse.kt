package team.aliens.dms.kmp.core.network.notification.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FetchNotificationTopicStatusResponse(
    @SerialName("topic_groups") val topicGroups: List<TopicGroupResponse>,
) {
    @Serializable
    data class TopicGroupResponse(
        @SerialName("topic_group") val topicGroup: String,
        @SerialName("group_name") val groupName: String,
        @SerialName("topic_subscriptions") val topicSubscriptions: List<TopicSubscriptionResponse>,
    ) {
        @Serializable
        data class TopicSubscriptionResponse(
            @SerialName("topic") val topic: String,
            @SerialName("is_subscribed") val subscribed: Boolean,
        )
    }
}
