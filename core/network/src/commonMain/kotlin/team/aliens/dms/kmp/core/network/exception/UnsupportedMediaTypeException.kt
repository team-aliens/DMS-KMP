package team.aliens.dms.kmp.core.network.exception

class UnsupportedMediaTypeException : NetworkException(
    code = 415,
    message = "Unsupported media type",
)
