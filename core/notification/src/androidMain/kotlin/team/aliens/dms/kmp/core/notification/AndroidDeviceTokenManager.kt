package team.aliens.dms.kmp.core.notification

import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import team.aliens.dms.kmp.core.datastore.devicetoken.DeviceTokenPreferencesDataSource
import team.aliens.dms.kmp.core.domain.usecase.notification.CancelFcmDeviceTokenRegistrationUseCase
import team.aliens.dms.kmp.core.domain.usecase.notification.RegisterFcmDeviceTokenUseCase

internal class AndroidDeviceTokenManager(
    private val deviceTokenPreferencesDataSource: DeviceTokenPreferencesDataSource,
    private val registerFcmDeviceTokenUseCase: RegisterFcmDeviceTokenUseCase,
    private val cancelFcmDeviceTokenRegistrationUseCase: CancelFcmDeviceTokenRegistrationUseCase,
) : DeviceTokenManager {

    override suspend fun registerToken(token: String?) {
        val deviceToken = token ?: FirebaseMessaging.getInstance().token.await()
        deviceTokenPreferencesDataSource.storeDeviceToken(deviceToken)
        registerFcmDeviceTokenUseCase(deviceToken)
    }

    override suspend fun awaitToken(timeoutMs: Long): String? =
        deviceTokenPreferencesDataSource.loadDeviceToken()
            ?: runCatching { FirebaseMessaging.getInstance().token.await() }.getOrNull()

    override suspend fun unregisterToken() {
        deviceTokenPreferencesDataSource.loadDeviceToken()?.let { token ->
            cancelFcmDeviceTokenRegistrationUseCase(token)
            deviceTokenPreferencesDataSource.clearDeviceToken()
        }
    }
}
