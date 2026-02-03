package team.aliens.dms.kmp.core.data.notification.repository

import team.aliens.dms.kmp.core.data.notification.mapper.toModel
import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.NotificationType
import team.aliens.dms.kmp.core.network.notification.datasource.NetworkNotificationDataSource
import team.aliens.dms.kmp.core.network.notification.model.request.BatchUpdateNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.CancelFcmDeviceTokenRegistrationRequest
import team.aliens.dms.kmp.core.network.notification.model.request.RegisterFcmDeviceTokenRequest
import team.aliens.dms.kmp.core.network.notification.model.request.SubscribeNotificationTopicRequest
import team.aliens.dms.kmp.core.network.notification.model.request.UnsubscribeNotificationTopicRequest

internal class NotificationRepositoryImpl(
    private val networkNotificationDataSource: NetworkNotificationDataSource,
) : NotificationRepository {
    override suspend fun registerFcmDeviceToken(deviceToken: String): Result<Unit> =
        networkNotificationDataSource.registerFcmDeviceToken(
            RegisterFcmDeviceTokenRequest(body = RegisterFcmDeviceTokenRequest.Body(deviceToken = deviceToken))
        )

    override suspend fun cancelFcmDeviceTokenRegistration(deviceToken: String): Result<Unit> =
        networkNotificationDataSource.cancelFcmDeviceTokenRegistration(
            CancelFcmDeviceTokenRegistrationRequest(body = CancelFcmDeviceTokenRegistrationRequest.Body(deviceToken = deviceToken))
        )

    override suspend fun subscribeNotificationTopic(deviceToken: String, topic: NotificationType): Result<Unit> =
        networkNotificationDataSource.subscribeNotificationTopic(
            SubscribeNotificationTopicRequest(
                body = SubscribeNotificationTopicRequest.Body(
                    deviceToken = deviceToken,
                    topic = topic.name
                )
            )
        )

    override suspend fun unsubscribeNotificationTopic(deviceToken: String, topic: NotificationType): Result<Unit> =
        networkNotificationDataSource.unsubscribeNotificationTopic(
            UnsubscribeNotificationTopicRequest(
                body = UnsubscribeNotificationTopicRequest.Body(
                    deviceToken = deviceToken,
                    topic = topic.name
                )
            )
        )

    override suspend fun batchUpdateNotificationTopic(topics: List<NotificationTopicStatusModel.TopicGroup.TopicSubscription>): Result<Unit> =
        networkNotificationDataSource.batchUpdateNotificationTopic(
            BatchUpdateNotificationTopicRequest(
                body = BatchUpdateNotificationTopicRequest.Body(
                    topics = topics.map {
                        BatchUpdateNotificationTopicRequest.Body.BodyDetail(
                            topic = it.topic.name,
                            subscribed = it.isSubscribed
                        )
                    }
                )
            )
        )

    override suspend fun fetchNotificationTopicStatus(deviceToken: String): Result<NotificationTopicStatusModel> =
        networkNotificationDataSource.fetchNotificationTopicStatus(deviceToken).map { it.toModel() }

    override suspend fun fetchNotifications(): Result<NotificationsModel> =
        networkNotificationDataSource.fetchNotifications().map { it.toModel() }

    override suspend fun updateNotificationReadStatus(notificationId: String): Result<Unit> =
        networkNotificationDataSource.updateNotificationReadStatus(notificationId)
}