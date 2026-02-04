package team.aliens.dms.kmp.service

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import team.aliens.dms.kmp.core.notification.DeviceTokenManager
import team.aliens.dms.kmp.core.notification.NotificationManager

class DmsMessagingService : FirebaseMessagingService() {

    private val deviceTokenManager: DeviceTokenManager by inject()
    private val notificationManager: NotificationManager by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            deviceTokenManager.registerToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let {
            notificationManager.showNotification(
                title = it.title ?: "DMS",
                body = it.body ?: "새로운 알림이 있습니다.",
                data = message.data,
            )
        }
    }
}
