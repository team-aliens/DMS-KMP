package team.aliens.dms.kmp.core.data.image.repository

import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.LocalImageDataSource
import team.aliens.dms.kmp.core.model.image.CropRect
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

internal class ImageRepositoryImpl(
    private val localImageDataSource: LocalImageDataSource,
    private val imageCropper: ImageCropper,
) : ImageRepository {

    override suspend fun getGalleryImages(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>> = runCatching {
        localImageDataSource.getImages(page = page, pageSize = pageSize)
    }

    override suspend fun getImageBytes(id: String): Result<ByteArray> = runCatching {
        localImageDataSource.getImageBytes(id = id)
    }

    override suspend fun getImageUri(id: String): Result<String> = runCatching {
        localImageDataSource.getImageUri(id = id)
    }

    override suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray> = runCatching {
        imageCropper.cropImage(
            imageBytes = imageBytes,
            cropRect = cropRect,
            outputWidth = outputWidth,
            outputHeight = outputHeight,
        )
    }
}
