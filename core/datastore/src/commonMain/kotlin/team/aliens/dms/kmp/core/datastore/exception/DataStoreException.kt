package team.aliens.dms.kmp.core.datastore.exception

/**
 * DataStore 작업 중 발생하는 일반적인 예외를 나타냅니다.
 *
 * @param message 예외에 대한 설명 메시지
 */
open class DataStoreException(message: String?) : RuntimeException(message)
