package team.aliens.dms.kmp.core.common.exception.network

class NotFoundException : NetworkException(
    code = 404,
    message = "Not found",
)
