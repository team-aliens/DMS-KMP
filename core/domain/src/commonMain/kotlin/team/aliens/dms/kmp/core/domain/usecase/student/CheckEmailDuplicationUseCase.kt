package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class CheckEmailDuplicationUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(email: String) =
        studentRepository.checkEmailDuplication(email = email)
}
