package team.aliens.dms.kmp.core.data.user.repository

import team.aliens.dms.kmp.core.data.user.model.toRequest
import team.aliens.dms.kmp.core.network.user.datasource.NetworkUserDataSource

internal class UserRepositoryImpl(
    private val networkUserDataSource: NetworkUserDataSource,
) : UserRepository {
    override suspend fun editPassword(
        password: String,
        newPassword: String,
    ): Result<Unit> =
        networkUserDataSource.editPassword(
            request = password.toRequest(newPassword = newPassword),
        )

    override suspend fun comparePassword(password: String): Result<Unit> = networkUserDataSource.comparePassword(password)
}
