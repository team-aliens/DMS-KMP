package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository

class GetImageUriUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(id: String): Result<String> =
        imageRepository.getImageUri(id = id)
}
