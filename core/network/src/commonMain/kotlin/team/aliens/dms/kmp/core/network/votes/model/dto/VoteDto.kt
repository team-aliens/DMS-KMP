package team.aliens.dms.kmp.core.network.votes.model.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class VoteDto(
    val id: String,
    val topicName: String,
    val description: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val voteType: VoteTypeDto,
)
