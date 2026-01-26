package team.aliens.dms.kmp.core.model.notification

data class NotificationTopicStatusModel(
    val topicGroups: List<TopicGroup>,
) {
    data class TopicGroup(
        val topicGroup: String,
        val groupName: String,
        val topicSubscriptions: List<TopicSubscription>,
    ) {
        data class TopicSubscription(
            val topic: String,
            val isSubscribed: Boolean,
        )
    }
}
