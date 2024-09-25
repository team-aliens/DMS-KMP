package team.aliens.dms.kmp.core.datastore.jwt.exception

import team.aliens.dms.kmp.core.datastore.exception.LoadFailureException

/**
 * JWT 토큰을 찾을 수 없을 때 발생하는 예외의 기본 클래스입니다.
 *
 * @property message 예외 메시지
 */
internal sealed class TokenNotFoundException(message: String?) : LoadFailureException(message)

internal class AccessTokenNotFoundException : TokenNotFoundException("Access token not found")

internal class RefreshTokenNotFoundException : TokenNotFoundException("Refresh token not found")
