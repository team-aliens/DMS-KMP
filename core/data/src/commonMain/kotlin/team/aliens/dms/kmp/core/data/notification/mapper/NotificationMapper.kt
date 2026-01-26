package team.aliens.dms.kmp.core.data.notification.mapper

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import team.aliens.dms.kmp.core.data.points.mapper.toModel
import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.NotificationGroupType
import team.aliens.dms.kmp.core.model.type.NotificationType
import team.aliens.dms.kmp.core.network.notification.dto.NotificationGroupTypeDto
import team.aliens.dms.kmp.core.network.notification.dto.NotificationTypeDto
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationTopicStatusResponse
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationsResponse
import team.aliens.dms.kmp.core.util.now
import team.aliens.dms.kmp.core.util.toElapsedText

internal fun FetchNotificationsResponse.toModel(): NotificationsModel =
    NotificationsModel(
        notifications = this.notifications.map { it.toModel() }
    )

private fun FetchNotificationsResponse.NotificationResponse.toModel(): NotificationsModel.NotificationModel =
    NotificationsModel.NotificationModel(
        id = id,
        topic = topic.toModel(),
        pointDetailTopic = pointDetailTopic?.toModel(),
        linkId = linkId,
        title = title,
        content = content,
        createdAt = createdAt,
        isRead = isRead,
        elapsedText = LocalDateTime.parse(createdAt).toElapsedText(now)
    )


internal fun FetchNotificationTopicStatusResponse.toModel(): NotificationTopicStatusModel =
    NotificationTopicStatusModel(
        topicGroups = this.topicGroups.map { it.toModel() }
    )

private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.toModel(): NotificationTopicStatusModel.TopicGroup =
    NotificationTopicStatusModel.TopicGroup(
        topicGroup = this.topicGroup.toModel(),
        groupName = this.groupName,
        topicSubscriptions = this.topicSubscriptions.map { it.toModel() }
    )


private fun FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse.toModel(): NotificationTopicStatusModel.TopicGroup.TopicSubscription =
    NotificationTopicStatusModel.TopicGroup.TopicSubscription(
        topic = this.topic.toModel(),
        isSubscribed = this.subscribed
    )

internal fun NotificationGroupTypeDto.toModel(): NotificationGroupType = when (this) {
    NotificationGroupTypeDto.NOTICE -> NotificationGroupType.NOTICE
    NotificationGroupTypeDto.POINT -> NotificationGroupType.POINT
    NotificationGroupTypeDto.STUDY_ROOM -> NotificationGroupType.STUDY_ROOM
    NotificationGroupTypeDto.OUTING -> NotificationGroupType.OUTING
}

internal fun NotificationTypeDto.toModel(): NotificationType = when (this) {
    NotificationTypeDto.NOTICE -> NotificationType.NOTICE
    NotificationTypeDto.POINT -> NotificationType.POINT
    NotificationTypeDto.STUDY_ROOM_TIME_SLOT -> NotificationType.STUDY_ROOM_TIME_SLOT
    NotificationTypeDto.STUDY_ROOM_APPLY -> NotificationType.STUDY_ROOM_APPLY
    NotificationTypeDto.OUTING -> NotificationType.OUTING
}
