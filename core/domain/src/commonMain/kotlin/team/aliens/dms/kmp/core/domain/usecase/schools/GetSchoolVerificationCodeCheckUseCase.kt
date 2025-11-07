package team.aliens.dms.kmp.core.domain.usecase.schools

import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepository

class GetSchoolVerificationCodeCheckUseCase(
    private val schoolsRepository: SchoolsRepository,
) {
    suspend operator fun invoke(schoolCode: String) =
        schoolsRepository.getSchoolVerificationCodeCheck(schoolCode = schoolCode)
}
