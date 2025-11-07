package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository

class CheckIdExistsUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(accountId: String) =
        authRepository.checkIdExists(accountId = accountId)
}
