package team.aliens.dms.kmp.core.notification

import team.aliens.dms.kmp.core.datastore.devicetoken.DeviceTokenPreferencesDataSource
import team.aliens.dms.kmp.core.domain.usecase.notification.CancelFcmDeviceTokenRegistrationUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.RegisterFcmDeviceTokenUseCase

internal class IosDeviceTokenManager(
    private val deviceTokenPreferencesDataSource: DeviceTokenPreferencesDataSource,
    private val registerFcmDeviceTokenUseCase: RegisterFcmDeviceTokenUseCase,
    private val cancelFcmDeviceTokenRegistrationUseCase: CancelFcmDeviceTokenRegistrationUseCase,
) : DeviceTokenManager {

    override suspend fun registerToken(token: String?) {
        // iOS에서는 token이 항상 AppDelegate에서 전달됨
        token?.let { deviceToken ->
            deviceTokenPreferencesDataSource.storeDeviceToken(deviceToken)
            registerFcmDeviceTokenUseCase(deviceToken)
        }
    }

    override suspend fun unregisterToken() {
        deviceTokenPreferencesDataSource.loadDeviceToken()?.let { token ->
            cancelFcmDeviceTokenRegistrationUseCase(token)
            deviceTokenPreferencesDataSource.clearDeviceToken()
        }
    }
}
