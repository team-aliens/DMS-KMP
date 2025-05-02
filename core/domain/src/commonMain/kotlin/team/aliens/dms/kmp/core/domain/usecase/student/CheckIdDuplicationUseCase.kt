package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class CheckIdDuplicationUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(id: String) = studentRepository.checkIdDuplication(
        id = id,
    )
}
