package team.aliens.dms.kmp.core.network.exception

class ServiceUnavailableException : NetworkException(
    code = 503,
    message = "Service Unavailable",
)
