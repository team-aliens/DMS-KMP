package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.data.notification.repository.NotificationRepository

class RegisterFcmDeviceTokenUseCase(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(deviceToken: String) =
        notificationRepository.registerFcmDeviceToken(deviceToken = deviceToken)
}
