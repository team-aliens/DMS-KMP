package team.aliens.dms.kmp.core.media

import team.aliens.dms.kmp.core.model.image.GalleryImageModel

interface LocalImageDataSource {
    suspend fun getImages(page: Int, pageSize: Int): List<GalleryImageModel>
    suspend fun getImageBytes(id: String): ByteArray
    suspend fun getImageUri(id: String): String
}
