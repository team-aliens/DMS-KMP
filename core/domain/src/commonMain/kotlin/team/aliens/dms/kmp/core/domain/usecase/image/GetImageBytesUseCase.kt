package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository

class GetImageBytesUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(id: String): Result<ByteArray> =
        imageRepository.getImageBytes(id = id)
}
