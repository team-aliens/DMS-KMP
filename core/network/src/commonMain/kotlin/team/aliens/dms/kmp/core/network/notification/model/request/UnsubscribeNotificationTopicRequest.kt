package team.aliens.dms.kmp.core.network.notification.model.request

data class UnsubscribeNotificationTopicRequest(
    val body: Body,
) {
    data class Body(
        val deviceToken: String,
        val topic: String,
    )
}
