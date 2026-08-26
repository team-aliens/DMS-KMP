package team.aliens.dms.kmp.core.data.remains.repository

import team.aliens.dms.kmp.core.data.remains.mapper.toModel
import team.aliens.dms.kmp.core.model.remains.AppliedRemainsOptionModel
import team.aliens.dms.kmp.core.model.remains.RemainsApplicationTimeModel
import team.aliens.dms.kmp.core.model.remains.RemainsOptionModel
import team.aliens.dms.kmp.core.network.remains.datasource.NetworkRemainsDataSource
import team.aliens.dms.kmp.core.network.remains.model.request.UpdateRemainsOptionRequest

internal class RemainsRepositoryImpl(
    private val networkRemainsDataSource: NetworkRemainsDataSource,
) : RemainsRepository {
    override suspend fun updateRemainsOption(remainOptionId: String): Result<Unit> =
        networkRemainsDataSource.updateRemainsOption(
            request =
                UpdateRemainsOptionRequest(
                    path = UpdateRemainsOptionRequest.Path(remainOptionId = remainOptionId),
                ),
        )

    override suspend fun getAppliedRemainsOption(): Result<AppliedRemainsOptionModel> =
        networkRemainsDataSource.getAppliedRemainsOption().map { it.toModel() }

    override suspend fun getRemainsApplicationTime(): Result<RemainsApplicationTimeModel> =
        networkRemainsDataSource.getRemainsApplicationTime().map { it.toModel() }

    override suspend fun getRemainsOptions(): Result<List<RemainsOptionModel>> =
        networkRemainsDataSource.getRemainsOptions().map { it.toModel() }
}
