package team.aliens.dms.kmp.core.common.exception.network

class BadRequestException : NetworkException(
    code = 400,
    message = "Bad request",
)
