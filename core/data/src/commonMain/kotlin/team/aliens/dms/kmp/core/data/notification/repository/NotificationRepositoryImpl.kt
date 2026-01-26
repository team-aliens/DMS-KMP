package team.aliens.dms.kmp.core.data.notification.repository

import team.aliens.dms.kmp.core.model.notification.NotificationModel
import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.kmp.core.network.notification.model.request.BatchUpdateNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.kmp.core.network.notification.model.request.RegisterFcmDeviceTokenRequest
import team.aliens.dms.kmp.core.network.notification.model.request.SubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.UnsubscribeNotificationTopicRequest

class NotificationRepositoryImpl(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
) : NotificationRepository {
    override suspend fun registerFcmDeviceToken(request: RegisterFcmDeviceTokenRequest): Result<Unit> =
        networkNotificationDataSource.registerFcmDeviceToken(request)

    override suspend fun cancelFcmDeviceTokenRegistration(request: CancelFcmDeviceTokenRegistrationRequest): Result<Unit> =
        networkNotificationDataSource.cancelFcmDeviceTokenRegistration(request)

    override suspend fun subscribeNotificationTopic(request: SubscribeNotificationTopicRequest): Result<Unit> =
        networkNotificationDataSource.subscribeNotificationTopic(request)

    override suspend fun unsubscribeNotificationTopic(request: UnsubscribeNotificationTopicRequest): Result<Unit> =
        networkNotificationDataSource.unsubscribeNotificationTopic(request)

    override suspend fun batchUpdateNotificationTopic(request: BatchUpdateNotificationTopicRequest): Result<Unit> =
        networkNotificationDataSource.batchUpdateNotificationTopic(request)

    override suspend fun fetchNotificationTopicStatus(deviceToken: String): Result<NotificationTopicStatusModel> =
        networkNotificationDataSource.fetchNotificationTopicStatus(deviceToken)

    override suspend fun fetchNotifications(): Result<List<NotificationModel>> =
        networkNotificationDataSource.fetchNotifications()

    override suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit> =
        networkNotificationDataSource.updateNotificationReadStatus(notificationId)
}
