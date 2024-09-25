package team.aliens.dms.kmp.core.datastore.jwt.exception

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

sealed class CannotStoreTokenException(message: String?) : TransformFailureException(message)

class CannotStoreTokensException : CannotStoreTokenException("Cannot store tokens")

class CannotStoreAccessTokenException : CannotStoreTokenException("Cannot store access token")

class CannotStoreRefreshTokenException : CannotStoreTokenException("Cannot store refresh token")
