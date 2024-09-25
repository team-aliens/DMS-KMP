package team.aliens.dms.kmp.core.datastore.exception

/**
 * DataStore에서 데이터 변환 실패 시 발생하는 예외입니다.
 *
 * @param message 예외 메시지. 기본값은 "Transform failure"입니다.
 */
open class TransformFailureException(message: String? = "Transform failure") :
    DataStoreException(message)
