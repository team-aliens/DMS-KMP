package team.aliens.dms.kmp.core.network.exception

class InternalServerErrorException : NetworkException(
    code = 500,
    message = "Internal server error",
)
