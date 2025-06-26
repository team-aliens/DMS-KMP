package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository

class ReissueTokenUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        refreshToken: String,
    ) = authRepository.reissueToken(refreshToken = refreshToken)
}
