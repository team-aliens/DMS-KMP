package team.aliens.dms.kmp.core.common.exception.network

class UnAuthorizedException : NetworkException(
    code = 401,
    message = "Unauthorized",
)
