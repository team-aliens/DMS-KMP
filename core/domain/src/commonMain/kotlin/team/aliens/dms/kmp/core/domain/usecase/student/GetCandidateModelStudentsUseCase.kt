package team.aliens.dms.kmp.core.domain.usecase.student

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class GetCandidateModelStudentsUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(requestDate: LocalDate) = studentRepository.getCandidateModelStudents(requestDate = requestDate)
}
