package team.aliens.dms.kmp.core.domain.usecase.auth

import team.aliens.dms.kmp.core.model.type.EmailVerificationType
import team.aliens.dms.kmp.core.data.auth.repository.AuthRepository

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
