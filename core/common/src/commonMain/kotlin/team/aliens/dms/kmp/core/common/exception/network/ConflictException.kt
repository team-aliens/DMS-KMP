package team.aliens.dms.kmp.core.common.exception.network

class ConflictException : NetworkException(
    code = 409,
    message = "Conflict",
)
