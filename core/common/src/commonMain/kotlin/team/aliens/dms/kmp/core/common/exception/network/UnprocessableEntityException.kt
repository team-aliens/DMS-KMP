package team.aliens.dms.kmp.core.common.exception.network

class UnprocessableEntityException : NetworkException(
    code = 422,
    message = "Unprocessable Entity",
)
