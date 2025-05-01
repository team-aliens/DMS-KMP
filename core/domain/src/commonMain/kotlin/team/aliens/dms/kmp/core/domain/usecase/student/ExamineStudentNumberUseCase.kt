package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class ExamineStudentNumberUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        schoolId: String,
        grade: Int,
        classroom: Int,
        number: Int,
    ) = studentRepository.examineStudentNumber(
        schoolId = schoolId,
        grade = grade,
        classroom = classroom,
        number = number,
    )
}
