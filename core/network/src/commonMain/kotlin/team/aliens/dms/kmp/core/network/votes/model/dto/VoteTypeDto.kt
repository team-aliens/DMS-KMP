package team.aliens.dms.kmp.core.network.votes.model.dto

/**
 * 서버로부터 전달받은 투표 타입을 나타내는 열거형입니다.
 *
 * @property MODEL_STUDENT_VOTE 모범학생을 대상으로 한 투표
 * @property STUDENT_VOTE 특정 학생을 대상으로 한 투표
 * @property OPTION_VOTE 여러 선택지 중 하나를 선택하는 방식의 투표
 * @property APPROVAL_VOTE 찬반을 묻는 방식의 투표
 */
enum class VoteTypeDto {
    /** 모범학생 투표 */
    MODEL_STUDENT_VOTE,

    /** 학생 투표 */
    STUDENT_VOTE,

    /** 옵션 대상 투표 */
    OPTION_VOTE,

    /** 찬반 투표 */
    APPROVAL_VOTE,
}
