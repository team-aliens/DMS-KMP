package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class ResetPasswordUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        accountId: String,
        name: String,
        email: String,
        emailVerificationCode: String,
        newPassword: String,
    ) = studentRepository.resetPassword(
        accountId = accountId,
        name = name,
        email = email,
        emailVerificationCode = emailVerificationCode,
        newPassword = newPassword,
    )
}
