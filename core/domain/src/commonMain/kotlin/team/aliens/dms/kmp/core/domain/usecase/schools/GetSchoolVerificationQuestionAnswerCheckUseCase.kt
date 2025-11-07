package team.aliens.dms.kmp.core.domain.usecase.schools

import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepository

class GetSchoolVerificationQuestionAnswerCheckUseCase(
    private val schoolsRepository: SchoolsRepository,
) {
    suspend operator fun invoke(schoolId: String, answer: String) =
        schoolsRepository.getSchoolVerificationQuestionAnswerCheck(
            schoolId = schoolId,
            answer = answer,
        )
}
