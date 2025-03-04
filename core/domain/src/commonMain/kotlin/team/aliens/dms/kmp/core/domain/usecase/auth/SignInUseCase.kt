package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        accountId: String,
        password: String,
        deviceToken: String,
    ) = authRepository.signIn(
        accountId = accountId,
        password = password,
        deviceToken = deviceToken,
    )
}
