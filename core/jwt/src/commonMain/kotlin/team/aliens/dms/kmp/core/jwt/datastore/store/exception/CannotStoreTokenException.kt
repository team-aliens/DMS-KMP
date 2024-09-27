package team.aliens.dms.kmp.core.jwt.datastore.store.exception

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

/**
 * 토큰을 저장할 수 없을 때 발생하는 기본 예외 클래스입니다.
 */
internal sealed class CannotStoreTokenException(message: String?) : TransformFailureException(message)

/**
 * 토큰을 저장할 수 없을 때 발생하는 예외입니다.
 */
internal class CannotStoreTokensException : CannotStoreTokenException("Cannot store tokens")

/**
 * 액세스 토큰을 저장할 수 없을 때 발생하는 예외입니다.
 */
internal class CannotStoreAccessTokenException : CannotStoreTokenException("Cannot store access token")

/**
 * 리프레시 토큰을 저장할 수 없을 때 발생하는 예외입니다.
 */
internal class CannotStoreRefreshTokenException : CannotStoreTokenException("Cannot store refresh token")
