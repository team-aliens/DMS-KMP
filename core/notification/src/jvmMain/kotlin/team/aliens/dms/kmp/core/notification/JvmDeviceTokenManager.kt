package team.aliens.dms.kmp.core.notification

internal class JvmDeviceTokenManager : DeviceTokenManager {

    override suspend fun registerToken(token: String?) {
        // JVM (Desktop)에서는 FCM 미지원
    }

    override suspend fun unregisterToken() {
        // JVM (Desktop)에서는 FCM 미지원
    }
}
