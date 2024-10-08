package team.aliens.dms.kmp.core.network.exception

class RequestTimeoutException : NetworkException(
    code = 408,
    message = "Request timeout",
)
