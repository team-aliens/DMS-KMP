package team.aliens.dms.kmp.core.domain.usecase.schools

import team.aliens.dms.kmp.core.data.schools.repository.SchoolsRepository

class GetSchoolsUseCase(
    private val schoolsRepository: SchoolsRepository,
) {
    suspend operator fun invoke() = schoolsRepository.getSchools()
}
