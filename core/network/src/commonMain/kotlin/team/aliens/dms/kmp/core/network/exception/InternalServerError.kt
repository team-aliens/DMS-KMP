package team.aliens.dms.kmp.core.network.exception

class InternalServerError : NetworkException(
    code = 500,
    message = "Internal server error",
)
