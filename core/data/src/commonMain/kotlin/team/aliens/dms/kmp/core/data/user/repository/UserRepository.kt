package team.aliens.dms.kmp.core.data.user.repository

interface UserRepository {
    suspend fun editPassword(password: String, newPassword: String): Result<Unit>
    suspend fun comparePassword(password: String): Result<Unit>
}
