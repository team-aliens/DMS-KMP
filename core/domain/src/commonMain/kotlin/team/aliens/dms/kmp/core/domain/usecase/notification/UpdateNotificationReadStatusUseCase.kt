package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository

class UpdateNotificationReadStatusUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(notificationId: String) =
        notificationRepository.updateNotificationReadStatus(notificationId = notificationId)
}
