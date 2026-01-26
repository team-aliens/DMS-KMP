package team.aliens.dms.kmp.core.network.notification.model.request

data class BatchUpdateNotificationTopicRequest(
    val body: Body,
) {
    data class Body(
        val topics: List<BodyDetail>,
    ) {
        data class BodyDetail(
            val topic: String,
            val subscribed: Boolean,
        )
    }
}
