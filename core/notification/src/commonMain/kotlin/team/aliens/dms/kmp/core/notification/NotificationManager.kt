package team.aliens.dms.kmp.core.notification

interface NotificationManager {
    fun showNotification(title: String, body: String, data: Map<String, String> = emptyMap())
    fun initializeNotificationChannel()
}
