package team.aliens.dms.kmp.core.datastore.remain

interface RemainPreferencesDataSource {
    suspend fun loadRemain(): Result<String>

    suspend fun storeRemain(remain: String): Result<Unit>

    suspend fun clearRemain(): Result<Unit>
}
