package team.aliens.dms.kmp.core.network.exception

class ForbiddenException : NetworkException(
    code = 403,
    message = "Forbidden",
)
