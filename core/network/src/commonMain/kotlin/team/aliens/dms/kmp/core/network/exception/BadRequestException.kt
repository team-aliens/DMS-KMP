package team.aliens.dms.kmp.core.network.exception

class BadRequestException : NetworkException(
    code = 400,
    message = "Bad request",
)
