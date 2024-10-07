package team.aliens.dms.kmp.core.network.exception

class ConflictException : NetworkException(
    code = 409,
    message = "Conflict",
)
