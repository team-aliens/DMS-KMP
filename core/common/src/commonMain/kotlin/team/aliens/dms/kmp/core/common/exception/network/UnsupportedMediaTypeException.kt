package team.aliens.dms.kmp.core.common.exception.network

class UnsupportedMediaTypeException : NetworkException(
    code = 415,
    message = "Unsupported media type",
)
