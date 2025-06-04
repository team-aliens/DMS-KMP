package team.aliens.dms.kmp.core.data.remains.mapper

import team.aliens.dms.kmp.core.model.remains.AppliedRemainsOptionModel
import team.aliens.dms.kmp.core.model.remains.RemainsApplicationTimeModel
import team.aliens.dms.kmp.core.model.remains.RemainsOptionModel
import team.aliens.dms.kmp.core.network.remains.model.response.GetAppliedRemainsOptionResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsApplicationTimeResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsOptionsResponse

internal fun GetAppliedRemainsOptionResponse.toModel() = AppliedRemainsOptionModel(
    id = this.id,
    title = this.title,
)

internal fun GetRemainsApplicationTimeResponse.toModel() = RemainsApplicationTimeModel(
    startDayOfWeek = this.startDayOfWeek,
    startTime = this.startTime,
    endDayOfWeek = this.endDayOfWeek,
    endTime = this.endTime,
)

internal fun GetRemainsOptionsResponse.toModel() = this.remainOptions.map { it.toModel() }

private fun GetRemainsOptionsResponse.RemainsOption.toModel() = RemainsOptionModel(
    id = this.id,
    title = this.title,
    description = this.description,
    isApplied = this.isApplied,
)
