package team.aliens.dms.kmp.core.media

import team.aliens.dms.kmp.core.model.image.CropRect

interface ImageCropper {
    suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): ByteArray
}
