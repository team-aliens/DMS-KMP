package team.aliens.dms.kmp.core.data.remains.repository

import team.aliens.dms.kmp.core.model.remains.AppliedRemainsOptionModel
import team.aliens.dms.kmp.core.model.remains.RemainsApplicationTimeModel
import team.aliens.dms.kmp.core.model.remains.RemainsOptionModel

interface RemainsRepository {
    suspend fun updateRemainsOption(remainOptionId: String): Result<Unit>
    suspend fun getAppliedRemainsOption(): Result<AppliedRemainsOptionModel>
    suspend fun getRemainsApplicationTime(): Result<RemainsApplicationTimeModel>
    suspend fun getRemainsOptions(): Result<List<RemainsOptionModel>>
}
