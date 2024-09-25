package team.aliens.dms.kmp.core.jwt.datastore.store.exception

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

/**
 * 토큰 만료 정보를 저장하는 데 실패했을 때 발생하는 예외의 기본 클래스입니다.
 *
 * @param message 예외 메시지
 */
internal sealed class CannotStoreTokenExpirationException(message: String?) :
    TransformFailureException(message)

internal class CannotStoreAccessTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store access token expiration")

internal class CannotStoreRefreshTokenExpirationException :
    CannotStoreTokenExpirationException("Cannot store refresh token expiration")
