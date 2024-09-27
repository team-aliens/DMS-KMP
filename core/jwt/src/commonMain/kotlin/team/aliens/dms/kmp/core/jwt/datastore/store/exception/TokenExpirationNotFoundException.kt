package team.aliens.dms.kmp.core.jwt.datastore.store.exception

import team.aliens.dms.kmp.core.datastore.exception.LoadFailureException

/**
 * 토큰 만료 정보를 찾을 수 없을 때 발생하는 예외의 기본 클래스입니다.
 */
internal sealed class TokenExpirationNotFoundException(message: String?) : LoadFailureException(message)

/**
 * 액세스 토큰의 만료 정보를 찾을 수 없을 때 발생하는 예외입니다.
 */
internal class AccessTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Access token expiration not found")

/**
 * 리프레시 토큰의 만료 정보를 찾을 수 없을 때 발생하는 예외입니다.
 */
internal class RefreshTokenExpirationNotFoundException :
    TokenExpirationNotFoundException("Refresh token expiration not found")
