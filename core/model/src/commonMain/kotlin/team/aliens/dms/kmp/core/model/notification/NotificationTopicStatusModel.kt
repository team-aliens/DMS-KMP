package team.aliens.dms.kmp.core.model.notification

import team.aliens.dms.kmp.core.model.type.NotificationGroupType
import team.aliens.dms.kmp.core.model.type.NotificationType

data class NotificationTopicStatusModel(
    val topicGroups: List<TopicGroup>,
) {
    data class TopicGroup(
        val topicGroup: NotificationGroupType,
        val groupName: String,
        val topicSubscriptions: List<TopicSubscription>,
    ) {
        data class TopicSubscription(
            val topic: NotificationType,
            val isSubscribed: Boolean,
        )
    }
}
