package team.aliens.dms.kmp.core.notification

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNUserNotificationCenter

internal class IosNotificationManager : NotificationManager {

    override fun showNotification(title: String, body: String, data: Map<String, String>) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
        }

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = "dms_notification_${currentTimeMillis()}",
            content = content,
            trigger = null,
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { error ->
            error?.let {
                println("Failed to show notification: ${it.localizedDescription}")
            }
        }
    }

    override fun initializeNotificationChannel() {
        // iOS에서는 채널 개념이 없음 - 권한 요청은 별도로 처리
    }

    private fun currentTimeMillis(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }
}
