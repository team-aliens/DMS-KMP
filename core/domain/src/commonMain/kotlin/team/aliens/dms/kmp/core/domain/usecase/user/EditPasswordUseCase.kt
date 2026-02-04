package team.aliens.dms.kmp.core.domain.usecase.user

import team.aliens.dms.kmp.core.data.user.repository.UserRepository

class EditPasswordUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(password: String, newPassword: String): Result<Unit> =
        userRepository.editPassword(
            password = password,
            newPassword = newPassword,
        )
}
