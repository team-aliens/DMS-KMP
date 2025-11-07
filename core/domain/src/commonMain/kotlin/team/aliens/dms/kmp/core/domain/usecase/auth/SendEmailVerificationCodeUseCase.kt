package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository
import team.aliens.dms.kmp.core.model.type.EmailVerificationType

class SendEmailVerificationCodeUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        email: String,
        type: EmailVerificationType,
    ) = authRepository.sendEmailVerificationCode(
        email = email,
        type = type,
    )
}
