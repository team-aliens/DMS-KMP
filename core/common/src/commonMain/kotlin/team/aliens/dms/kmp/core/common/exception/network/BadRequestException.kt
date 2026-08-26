package team.aliens.dms.kmp.core.common.exception.network

class BadRequestException(
    val errorCode: String? = null,
) : NetworkException(
        code = 400,
        message = "Bad request",
    )
