package team.aliens.dms.kmp.core.network.remains.datasource

import team.aliens.dms.kmp.core.network.remains.model.request.UpdateRemainsOptionRequest
import team.aliens.dms.kmp.core.network.remains.model.response.GetAppliedRemainsOptionResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsApplicationTimeResponse
import team.aliens.dms.kmp.core.network.remains.model.response.GetRemainsOptionsResponse

interface NetworkRemainsDataSource {
    suspend fun updateRemainsOption(request: UpdateRemainsOptionRequest): Result<Unit>
    suspend fun getAppliedRemainsOption(): Result<GetAppliedRemainsOptionResponse>
    suspend fun getRemainsApplicationTime(): Result<GetRemainsApplicationTimeResponse>
    suspend fun getRemainsOptions(): Result<GetRemainsOptionsResponse>
}
