package team.aliens.dms.kmp.core.notification

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeoutOrNull
import team.aliens.dms.kmp.core.datastore.devicetoken.DeviceTokenPreferencesDataSource
import team.aliens.dms.kmp.core.domain.usecase.notification.CancelFcmDeviceTokenRegistrationUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.RegisterFcmDeviceTokenUseCase

internal class IosDeviceTokenManager(
    private val deviceTokenPreferencesDataSource: DeviceTokenPreferencesDataSource,
    private val registerFcmDeviceTokenUseCase: RegisterFcmDeviceTokenUseCase,
    private val cancelFcmDeviceTokenRegistrationUseCase: CancelFcmDeviceTokenRegistrationUseCase,
) : DeviceTokenManager {

    private val tokenDeferred = CompletableDeferred<String>()

    override suspend fun registerToken(token: String?) {
        token?.let { deviceToken ->
            if (!tokenDeferred.isCompleted) {
                tokenDeferred.complete(deviceToken)
            }
            deviceTokenPreferencesDataSource.storeDeviceToken(deviceToken)
            runCatching { registerFcmDeviceTokenUseCase(deviceToken) }
        }
    }

    override suspend fun awaitToken(timeoutMs: Long): String? {
        deviceTokenPreferencesDataSource.loadDeviceToken()?.let { return it }
        return withTimeoutOrNull(timeoutMs) { tokenDeferred.await() }
    }

    override suspend fun unregisterToken() {
        deviceTokenPreferencesDataSource.loadDeviceToken()?.let { token ->
            cancelFcmDeviceTokenRegistrationUseCase(token)
            deviceTokenPreferencesDataSource.clearDeviceToken()
        }
    }
}
