package team.aliens.dms.kmp.core.data.notification.repository

import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.NotificationType

interface NotificationRepository {
    suspend fun registerFcmDeviceToken(deviceToken: String): Result<Unit>

    suspend fun cancelFcmDeviceTokenRegistration(
        deviceToken: String
    ): Result<Unit>

    suspend fun subscribeNotificationTopic(deviceToken: String, topic: NotificationType): Result<Unit>

    suspend fun unsubscribeNotificationTopic(deviceToken: String, topic: NotificationType): Result<Unit>

    suspend fun batchUpdateNotificationTopic(topics: List<NotificationTopicStatusModel.TopicGroup.TopicSubscription>): Result<Unit>

    suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<NotificationTopicStatusModel>

    suspend fun fetchNotifications(): Result<NotificationsModel>

    suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit>
}