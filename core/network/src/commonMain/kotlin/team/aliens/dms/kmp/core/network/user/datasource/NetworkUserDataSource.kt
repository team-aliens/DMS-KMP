package team.aliens.dms.kmp.core.network.user.datasource

import team.aliens.dms.kmp.core.network.user.model.EditPasswordRequest

interface NetworkUserDataSource {
    suspend fun editPassword(request: EditPasswordRequest): Result<Unit>

    suspend fun comparePassword(password: String): Result<Unit>
}