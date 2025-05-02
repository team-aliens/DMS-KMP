package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class SignUpUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        schoolVerificationCode: String,
        schoolVerificationAnswer: String,
        email: String,
        emailVerificationCode: String,
        grade: Int,
        classRoom: Int,
        number: Int,
        accountId: String,
        password: String,
        profileImageUrl: String?,
    ) = studentRepository.signUp(
        schoolVerificationCode = schoolVerificationCode,
        schoolVerificationAnswer = schoolVerificationAnswer,
        email = email,
        emailVerificationCode = emailVerificationCode,
        grade = grade,
        classRoom = classRoom,
        number = number,
        accountId = accountId,
        password = password,
        profileImageUrl = profileImageUrl,
    )
}
