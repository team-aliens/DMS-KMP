package team.aliens.dms.kmp.core.media

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

internal class AndroidLocalImageDataSource(
    private val contentResolver: ContentResolver,
) : LocalImageDataSource {

    override suspend fun getImages(
        page: Int,
        pageSize: Int,
    ): List<GalleryImageModel> = withContext(Dispatchers.IO) {
        val images = mutableListOf<GalleryImageModel>()
        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media._ID)

        contentResolver.query(
            contentUri,
            projection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC",
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val imageUri = ContentUris.withAppendedId(contentUri, id)
                images.add(
                    GalleryImageModel(
                        id = id.toString(),
                        uri = imageUri.toString(),
                        dateAdded = 0L,
                        width = 0,
                        height = 0,
                    ),
                )
            }
        }

        images
    }

    override suspend fun getImageBytes(id: String): ByteArray = withContext(Dispatchers.IO) {
        val uri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id.toLong(),
        )
        contentResolver.openInputStream(uri)?.use { it.readBytes() }
            ?: throw IllegalStateException("Failed to read image bytes for id: $id")
    }

    override suspend fun getImageUri(id: String): String {
        return ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            id.toLong(),
        ).toString()
    }
}
