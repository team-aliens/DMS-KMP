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
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
        )

        val bundle = android.os.Bundle().apply {
            putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)
            putInt(ContentResolver.QUERY_ARG_OFFSET, (page - 1) * pageSize)
            putStringArray(
                ContentResolver.QUERY_ARG_SORT_COLUMNS,
                arrayOf(MediaStore.Images.Media.DATE_ADDED),
            )
            putInt(
                ContentResolver.QUERY_ARG_SORT_DIRECTION,
                ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
            )
        }

        val cursor = contentResolver.query(
            contentUri,
            projection,
            bundle,
            null,
        )

        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateAddedColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val widthColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
            val heightColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val dateAdded = it.getLong(dateAddedColumn)
                val width = it.getInt(widthColumn)
                val height = it.getInt(heightColumn)
                val imageUri = ContentUris.withAppendedId(contentUri, id)
                images.add(
                    GalleryImageModel(
                        id = id.toString(),
                        uri = imageUri.toString(),
                        dateAdded = dateAdded,
                        width = width,
                        height = height,
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
