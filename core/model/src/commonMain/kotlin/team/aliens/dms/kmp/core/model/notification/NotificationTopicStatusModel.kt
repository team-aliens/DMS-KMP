package team.aliens.dms.kmp.core.model.notification

import team.aliens.dms.kmp.core.model.type.NotificationGroupType
import team.aliens.dms.kmp.core.model.type.NotificationType

data class NotificationTopicStatusModel(
    val topicGroups: List<TopicGroup> = emptyList(),
) {
    data class TopicGroup(
        val topicGroup: NotificationGroupType = NotificationGroupType.STUDY_ROOM,
        val groupName: String = "",
        val topicSubscriptions: List<TopicSubscription> = emptyList(),
    ) {
        data class TopicSubscription(
            val topic: NotificationType = NotificationType.STUDY_ROOM_APPLY,
            val isSubscribed: Boolean = false,
        )
    }
}
