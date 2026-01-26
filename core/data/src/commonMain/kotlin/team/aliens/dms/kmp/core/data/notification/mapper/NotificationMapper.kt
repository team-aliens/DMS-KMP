package team.aliens.dms.kmp.core.data.notification.mapper

import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.model.notification.NotificationTopicStatusModel
import team.aliens.dms.kmp.core.model.notification.NotificationTopicType
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.point.PointType
import team.aliens.dms.kmp.core.model.type.NotificationType
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationTopicStatusResponse
import team.aliens.dms.kmp.core.network.notification.model.response.FetchNotificationsResponse
import team.aliens.dms.kmp.core.network.notification.model.response.NotificationTopicTypeResponse
import team.aliens.dms.kmp.core.network.notification.model.response.PointTypeResponse

/**
 * '받은 알림 목록' 네트워크 응답(DTO)을 UI 모델로 변환합니다.
 */
internal fun FetchNotificationsResponse.toModel(): NotificationsModel =
    NotificationsModel(
        notifications = this.notifications.map { it.toModel() }
    )

/**
 * '개별 알림' 네트워크 응답(DTO)을 UI 모델로 변환합니다.
 */
internal fun FetchNotificationsResponse.NotificationResponse.toModel(): NotificationsModel.NotificationModel =
    NotificationsModel.NotificationModel(

    )

/**
 * '알림 구독 상태' 네트워크 응답(DTO)을 UI 모델로 변환합니다.
 */
internal fun FetchNotificationTopicStatusResponse.toModel(): NotificationTopicStatusModel =
    NotificationTopicStatusModel(
        deviceToken = this.deviceToken,
        topicGroups = this.topicGroups.map { it.toModel() }
    )

/**
 * '알림 주제 그룹' 네트워크 응답(DTO)을 UI 모델로 변환합니다.
 */
internal fun FetchNotificationTopicStatusResponse.TopicGroupResponse.toModel(): NotificationTopicStatusModel.TopicGroup =
    NotificationTopicStatusModel.TopicGroup(
        name = this.name,
        topicSubscriptions = this.topicSubscriptions.map { it.toModel() }
    )

/**
 * '개별 알림 구독 정보' 네트워크 응답(DTO)을 UI 모델로 변환합니다.
 */
internal fun FetchNotificationTopicStatusResponse.TopicGroupResponse.TopicSubscriptionResponse.toModel(): NotificationTopicStatusModel.TopicGroup.TopicSubscription =
    NotificationTopicStatusModel.TopicGroup.TopicSubscription(
        topic = this.topic.toModel(),
        isSubscribed = this.isSubscribed
    )

/**
 * '알림 주제 타입' 네트워크 응답(Enum)을 UI 모델(Enum)로 변환합니다.
 */
internal fun NotificationTopicTypeResponse.toModel(): NotificationTopicType = when (this) {
    NotificationTopicTypeResponse.NOTICE -> NotificationType.NOTICE
    NotificationTopicTypeResponse.POINT -> NotificationTopicType.POINT
    NotificationTopicTypeResponse.MEAL -> NotificationTopicType.MEAL
    NotificationTopicTypeResponse.SCHEDULE -> NotificationTopicType.SCHEDULE
    NotificationTopicTypeResponse.STUDY_ROOM -> NotificationTopicType.STUDY_ROOM
    NotificationTopicTypeResponse.REMAIN -> NotificationTopicType.REMAIN
    NotificationTopicTypeResponse.SURVEY -> NotificationTopicType.SURVEY
    NotificationTopicTypeResponse.ETC -> NotificationTopicType.ETC
}

/**
 * '상벌점 타입' 네트워크 응답(Enum)을 UI 모델(Enum)로 변환합니다.
 */
internal fun PointTypeResponse.toModel(): PointType = when (this) {
    PointTypeResponse.ALL -> PointType.ALL
    PointTypeResponse.BONUS -> PointType.BONUS
    PointTypeResponse.MINUS -> PointType.MINUS
}
