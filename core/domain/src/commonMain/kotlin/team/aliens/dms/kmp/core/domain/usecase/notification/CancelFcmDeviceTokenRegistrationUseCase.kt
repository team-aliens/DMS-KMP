package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository

class CancelFcmDeviceTokenRegistrationUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(deviceToken: String) =
        notificationRepository.cancelFcmDeviceTokenRegistration(deviceToken = deviceToken)
}
