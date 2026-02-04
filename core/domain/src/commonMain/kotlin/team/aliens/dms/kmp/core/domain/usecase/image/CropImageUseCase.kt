package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.model.image.CropRect

class CropImageUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray> =
        imageRepository.cropImage(
            imageBytes = imageBytes,
            cropRect = cropRect,
            outputWidth = outputWidth,
            outputHeight = outputHeight,
        )
}
