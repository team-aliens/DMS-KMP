package team.aliens.dms.kmp.core.network.notification.datasource

import team.aliens.dms.kmp.core.network.notification.model.request.BatchUpdateNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationTopicStatusResponse
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationsResponse
import team.aliens.dms.kmp.core.network.notification.model.request.RegisterFcmDeviceTokenRequest
import team.aliens.dms.kmp.core.network.notification.model.request.SubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.UnsubscribeNotificationTopicRequest

interface NetworkNotificationDataSource {
    suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit>

    suspend fun cancelFcmDeviceTokenRegistration(
        request: CancelFcmDeviceTokenRegistrationRequest,
    ): Result<Unit>

    suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit>

    suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit>

    suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit>

    suspend fun fetchNotificationTopicStatus(
        deviceToken: String,
    ): Result<FetchNotificationTopicStatusResponse>

    suspend fun fetchNotifications(): Result<FetchNotificationsResponse>

    suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit>
}
