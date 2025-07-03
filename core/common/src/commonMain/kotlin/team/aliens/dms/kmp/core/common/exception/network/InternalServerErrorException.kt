package team.aliens.dms.kmp.core.common.exception.network

class InternalServerErrorException : NetworkException(
    code = 500,
    message = "Internal server error",
)
