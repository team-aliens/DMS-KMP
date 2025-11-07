package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository
import team.aliens.dms.kmp.core.model.type.EmailVerificationType

class CheckEmailVerificationCodeUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        code: String,
        type: EmailVerificationType,
    ) = authRepository.checkEmailVerificationCode(
        email = email,
        code = code,
        type = type,
    )
}
