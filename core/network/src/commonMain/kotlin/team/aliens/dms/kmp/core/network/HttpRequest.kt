package team.aliens.dms.kmp.core.network

import io.ktor.http.HttpMethod

data class HttpRequest(
    val method: HttpMethod,
    val path: String,
)
