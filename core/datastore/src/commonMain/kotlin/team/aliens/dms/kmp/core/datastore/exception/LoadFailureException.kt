package team.aliens.dms.kmp.core.datastore.exception

/**
 * 데이터 저장소에서 데이터 로드 작업이 실패했을 때 발생하는 예외입니다.
 *
 * @param message 예외에 대한 상세 메시지. 지정되지 않으면 기본값으로 "Load failure"가 사용됩니다.
 */
open class LoadFailureException(message: String? = "Load failure") : DataStoreException(message)
