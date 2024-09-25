package team.aliens.dms.kmp.core.datastore.jwt.exception

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

sealed class CannotStoreTokenExpirationException(message: String?) :
    TransformFailureException(message)

class CannotStoreAccessTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store access token expiration")

class CannotStoreRefreshTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store refresh token expiration")
