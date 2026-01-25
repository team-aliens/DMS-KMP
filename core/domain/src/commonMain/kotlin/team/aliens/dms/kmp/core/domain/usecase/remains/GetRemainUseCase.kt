package team.aliens.dms.kmp.core.domain.usecase.remains

import team.aliens.dms.kmp.core.datastore.remain.RemainPreferencesDataSource

class GetRemainUseCase(
    private val remainPreferencesDataSource: RemainPreferencesDataSource,
) {
    suspend operator fun invoke() = remainPreferencesDataSource.loadRemain()
}
