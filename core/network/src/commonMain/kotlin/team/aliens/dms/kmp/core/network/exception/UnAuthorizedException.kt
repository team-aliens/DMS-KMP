package team.aliens.dms.kmp.core.network.exception

class UnAuthorizedException : NetworkException(
    code = 401,
    message = "Unauthorized",
)
