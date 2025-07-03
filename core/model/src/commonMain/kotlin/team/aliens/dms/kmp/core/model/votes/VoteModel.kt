package team.aliens.dms.kmp.core.model.votes

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.model.type.VoteType

@Serializable
data class VoteModel(
    val id: String,
    val topicName: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val voteType: VoteType,
)
