package team.aliens.dms.kmp.core.domain.usecase.user

import team.aliens.dms.kmp.core.data.user.repository.UserRepository

class ComparePasswordUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(password: String): Result<Unit> =
        userRepository.comparePassword(password)
}
