package team.aliens.dms.kmp.core.domain.usecase.schools

import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepository

class GetSchoolVerificationQuestionCheckUseCase(
    private val schoolsRepository: SchoolsRepository,
) {
    suspend operator fun invoke(schoolId: String) =
        schoolsRepository.getSchoolVerificationQuestionCheck(schoolId = schoolId)
}
