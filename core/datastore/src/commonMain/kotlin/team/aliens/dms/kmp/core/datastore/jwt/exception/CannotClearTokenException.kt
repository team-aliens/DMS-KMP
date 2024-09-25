package team.aliens.dms.kmp.core.datastore.jwt.exception

import team.aliens.dms.kmp.core.datastore.exception.TransformFailureException

/**
 * 토큰을 삭제할 수 없을 때 발생하는 기본 예외 클래스입니다.
 */
internal sealed class CannotClearTokenException(message: String?) : TransformFailureException(message)

/**
 * 토큰을 삭제할 수 없을 때 발생하는 예외입니다.
 */
internal class CannotClearTokensException : CannotClearTokenException("Cannot clear tokens")
