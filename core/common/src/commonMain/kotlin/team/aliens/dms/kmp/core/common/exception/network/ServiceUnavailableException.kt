package team.aliens.dms.kmp.core.common.exception.network

class ServiceUnavailableException : NetworkException(
    code = 503,
    message = "Service Unavailable",
)
