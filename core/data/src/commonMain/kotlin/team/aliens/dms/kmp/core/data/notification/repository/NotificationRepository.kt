package team.aliens.dms.kmp.core.data.notification.repository

import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.network.notification.model.request.BatchUpdateNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.kmp.core.network.notification.model.request.RegisterFcmDeviceTokenRequest
import team.aliens.dms.kmp.core.network.notification.model.request.SubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.UnsubscribeNotificationTopicRequest

interface NotificationRepository {
    suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit>

    suspend fun cancelFcmDeviceTokenRegistration(
        request: CancelFcmDeviceTokenRegistrationRequest,
    ): Result<Unit>

    suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit>

    suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit>

    suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit>

    suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<NotificationTopicStatusModel>

    suspend fun fetchNotifications(): Result<NotificationsModel>

    suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit>
}
