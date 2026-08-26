package team.aliens.dms.kmp.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import team.aliens.dms.kmp.core.notification.DeviceTokenManager

object KoinIosHelper : KoinComponent {
    private val deviceTokenManager: DeviceTokenManager by inject()

    fun registerDeviceToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenManager.registerToken(token)
        }
    }

    fun unregisterDeviceToken() {
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenManager.unregisterToken()
        }
    }
}
