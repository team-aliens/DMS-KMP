package team.aliens.dms.kmp.core.network.exception

class TooManyRequestsException : NetworkException(
    code = 429,
    message = "Too many requests",
)
