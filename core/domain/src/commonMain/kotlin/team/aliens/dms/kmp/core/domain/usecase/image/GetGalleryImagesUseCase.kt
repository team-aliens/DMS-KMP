package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

class GetGalleryImagesUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>> =
        imageRepository.getGalleryImages(
            page = page,
            pageSize = pageSize,
        )
}
