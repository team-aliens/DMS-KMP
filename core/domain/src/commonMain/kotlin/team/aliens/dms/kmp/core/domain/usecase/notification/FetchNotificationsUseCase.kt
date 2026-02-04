package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository

class FetchNotificationsUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke() = notificationRepository.fetchNotifications()
}
