package team.aliens.dms.kmp.core.datastore.jwt.exception

import team.aliens.dms.kmp.core.datastore.exception.LoadFailureException

sealed class TokenExpirationNotFoundException(message: String?) : LoadFailureException(message)

class AccessTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Access token expiration not found")

class RefreshTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Refresh token expiration not found")
