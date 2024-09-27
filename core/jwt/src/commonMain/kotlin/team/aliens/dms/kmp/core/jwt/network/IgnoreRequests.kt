package team.aliens.dms.kmp.core.jwt.network

import team.aliens.dms.kmp.core.network.HttpRequest

interface IgnoreRequests {
    val requests: List<HttpRequest>
}
