package team.aliens.dms.kmp.core.data.image.repository

import team.aliens.dms.kmp.core.model.image.CropRect
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

interface ImageRepository {
    suspend fun getGalleryImages(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>>

    suspend fun getImageBytes(id: String): Result<ByteArray>

    suspend fun getImageUri(id: String): Result<String>

    suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray>
}
