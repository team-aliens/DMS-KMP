package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository

class FetchNotificationTopicStatusUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(deviceToken: String) =
        notificationRepository.fetchNotificationTopicStatus(deviceToken = deviceToken)
}
