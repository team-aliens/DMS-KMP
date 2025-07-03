package team.aliens.dms.kmp.core.common.exception.network

class ForbiddenException : NetworkException(
    code = 403,
    message = "Forbidden",
)
