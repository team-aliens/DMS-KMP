package team.aliens.dms.kmp.core.common.exception.network

class RequestTimeoutException : NetworkException(
    code = 408,
    message = "Request timeout",
)
