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
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
        val offset = page * pageSize

        contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "$sortOrder LIMIT $pageSize OFFSET $offset",
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id,
                )
                images.add(
                    GalleryImageModel(
                        id = id.toString(),
                        uri = uri.toString(),
                        dateAdded = cursor.getLong(dateAddedColumn),
                        width = cursor.getInt(widthColumn),
                        height = cursor.getInt(heightColumn),
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
