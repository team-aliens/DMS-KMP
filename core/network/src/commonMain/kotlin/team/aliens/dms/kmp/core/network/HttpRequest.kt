package team.aliens.dms.kmp.core.network

import io.ktor.http.HttpMethod

/**
 * HTTP 요청을 나타내는 데이터 클래스입니다.
 *
 * @property method HTTP 요청 메서드
 * @property path 요청 경로
 */
data class HttpRequest(
    val method: HttpMethod,
    val path: String,
)
