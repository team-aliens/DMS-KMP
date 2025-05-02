package team.aliens.dms.kmp.core.domain.usecase.student

import team.aliens.dms.kmp.core.data.student.repository.StudentRepository

class EditProfileUseCase(
    private val studentRepository: StudentRepository,
) {
    suspend operator fun invoke(profileImageUrl: String) =
        studentRepository.editProfile(profileImageUrl = profileImageUrl)
}
