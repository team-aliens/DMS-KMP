package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class FindIdUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(
        schoolId: String,
        studentName: String,
        grade: Int,
        classRoom: Int,
        number: Int,
    ) = studentRepository.findId(
        schoolId = schoolId,
        studentName = studentName,
        grade = grade,
        classRoom = classRoom,
        number = number,
    )
}
