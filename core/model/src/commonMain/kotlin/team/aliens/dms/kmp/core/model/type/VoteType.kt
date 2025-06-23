package team.aliens.dms.kmp.core.model.type

import kotlinx.serialization.Serializable

@Serializable
enum class VoteType {
    MODEL_STUDENT_VOTE,
    STUDENT_VOTE,
    OPTION_VOTE,
    APPROVAL_VOTE,
}
