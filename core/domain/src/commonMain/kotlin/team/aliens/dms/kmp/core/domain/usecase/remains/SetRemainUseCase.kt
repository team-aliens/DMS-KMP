package team.aliens.dms.kmp.core.domain.usecase.remains

import team.aliens.dms.kmp.core.datastore.remain.RemainPreferencesDataSource

class SetRemainUseCase(
    private val remainPreferencesDataSource: RemainPreferencesDataSource,
) {
    suspend operator fun invoke(remain: String) = remainPreferencesDataSource.storeRemain(remain)
}
