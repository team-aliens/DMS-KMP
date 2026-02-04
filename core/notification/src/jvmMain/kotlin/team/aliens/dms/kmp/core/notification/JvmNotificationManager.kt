package team.aliens.dms.kmp.core.notification

internal class JvmNotificationManager : NotificationManager {

    override fun showNotification(title: String, body: String, data: Map<String, String>) {
        // JVM (Desktop)에서는 기본 알림 미지원
        println("Notification: $title - $body")
    }

    override fun initializeNotificationChannel() {
        // JVM (Desktop)에서는 채널 개념 없음
    }
}
