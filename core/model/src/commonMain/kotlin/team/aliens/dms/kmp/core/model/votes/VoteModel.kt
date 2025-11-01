package team.aliens.dms.kmp.core.model.votes

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.util.now

@Serializable
data class VoteModel(
    val id: String = "",
    val topicName: String = "",
    val description: String = "",
    val startTime: LocalDateTime = now,
    val endTime: LocalDateTime = now,
    val voteType: VoteType = VoteType.STUDENT_VOTE,
)
