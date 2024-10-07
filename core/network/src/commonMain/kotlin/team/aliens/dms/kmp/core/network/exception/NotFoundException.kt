package team.aliens.dms.kmp.core.network.exception

class NotFoundException : NetworkException(
    code = 404,
    message = "Not found",
)
