package team.aliens.dms.kmp.core.common.exception.network

class TooManyRequestsException : NetworkException(
    code = 429,
    message = "Too many requests",
)
