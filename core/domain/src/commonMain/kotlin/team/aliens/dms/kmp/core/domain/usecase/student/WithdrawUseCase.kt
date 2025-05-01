package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class WithdrawUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke() = studentRepository.withdraw()
}
