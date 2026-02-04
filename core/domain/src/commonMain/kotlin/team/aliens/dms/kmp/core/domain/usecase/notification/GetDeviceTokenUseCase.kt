package team.aliens.dms.kmp.core.domain.usecase.notification

import team.aliens.dms.kmp.core.datastore.devicetoken.DeviceTokenPreferencesDataSource

class GetDeviceTokenUseCase(
    private val deviceTokenPreferencesDataSource: DeviceTokenPreferencesDataSource,
) {
    suspend operator fun invoke(): String? =
        deviceTokenPreferencesDataSource.loadDeviceToken()
}
