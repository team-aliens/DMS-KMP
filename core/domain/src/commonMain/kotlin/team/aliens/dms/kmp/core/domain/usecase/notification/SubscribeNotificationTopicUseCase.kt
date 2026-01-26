package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository
import team.aliens.dms.kmp.core.model.type.NotificationType

class SubscribeNotificationTopicUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(
        deviceToken: String,
        topic: NotificationType,
    ) = notificationRepository.subscribeNotificationTopic(
        deviceToken = deviceToken,
        topic = topic,
    )
}
