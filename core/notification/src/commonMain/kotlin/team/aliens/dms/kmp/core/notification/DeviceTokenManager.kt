package team.aliens.dms.kmp.core.notification

interface DeviceTokenManager {
    /**
     * 토큰 등록 (앱 시작 시, 새 토큰 발급 시 모두 사용)
     * @param token null이면 플랫폼에서 토큰 가져옴, 있으면 해당 토큰 사용
     */
    suspend fun registerToken(token: String? = null)

    /**
     * 토큰 해제 (로그아웃 시)
     */
    suspend fun unregisterToken()

    /**
     * 저장된 토큰 반환. 없으면 토큰이 도착할 때까지 최대 [timeoutMs]ms 대기.
     */
    suspend fun awaitToken(timeoutMs: Long = 5_000L): String?
}
