package team.aliens.dms.kmp.core.data.votes.mapper

import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteItemModel
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.core.network.votes.model.dto.VoteDto
import team.aliens.dms.kmp.core.network.votes.model.dto.VoteItemDto
import team.aliens.dms.kmp.core.network.votes.model.dto.VoteTypeDto
import team.aliens.dms.kmp.core.network.votes.model.response.GetAllVotesResponse
import team.aliens.dms.kmp.core.network.votes.model.response.GetVoteItemsResponse

internal fun GetAllVotesResponse.toModel() = votingTopics.map { it.toModel() }

internal fun GetVoteItemsResponse.toModel() = votingOptions.map { it.toModel() }

private fun VoteDto.toModel() = VoteModel(
    id = id,
    topicName = topicName,
    description = description,
    startTime = startTime,
    endTime = endTime,
    voteType = voteType.toModel(),
)

private fun VoteItemDto.toModel() = VoteItemModel(
    id = id,
    votingOptionName = votingOptionName,
)

private fun VoteTypeDto.toModel() = when (this) {
    VoteTypeDto.MODEL_STUDENT_VOTE -> VoteType.MODEL_STUDENT_VOTE
    VoteTypeDto.OPTION_VOTE -> VoteType.OPTION_VOTE
    VoteTypeDto.STUDENT_VOTE -> VoteType.STUDENT_VOTE
    VoteTypeDto.APPROVAL_VOTE -> VoteType.APPROVAL_VOTE
}
