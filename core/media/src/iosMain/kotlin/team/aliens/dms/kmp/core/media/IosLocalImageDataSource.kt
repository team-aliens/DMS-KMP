package team.aliens.dms.kmp.core.media

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Photos.PHAsset
import platform.Photos.PHAssetMediaTypeImage
import platform.Photos.PHFetchOptions
import platform.Photos.PHImageManager
import platform.Photos.PHImageRequestOptions
import platform.Photos.PHImageRequestOptionsDeliveryModeHighQualityFormat
import platform.Photos.PHImageRequestOptionsVersionCurrent
import team.aliens.dms.kmp.core.model.image.GalleryImageModel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class IosLocalImageDataSource : LocalImageDataSource {

    override suspend fun getImages(
        page: Int,
        pageSize: Int,
    ): List<GalleryImageModel> = withContext(Dispatchers.IO) {
        val fetchOptions = PHFetchOptions().apply {
            sortDescriptors = listOf(
                platform.Foundation.NSSortDescriptor("creationDate", ascending = false),
            )
        }

        val result = PHAsset.fetchAssetsWithMediaType(
            mediaType = PHAssetMediaTypeImage,
            options = fetchOptions,
        )

        val startIndex = page * pageSize
        val endIndex = minOf(startIndex + pageSize, result.count().toInt())
        val images = mutableListOf<GalleryImageModel>()

        for (i in startIndex until endIndex) {
            val asset = result.objectAtIndex(i.toULong()) as PHAsset
            images.add(
                GalleryImageModel(
                    id = asset.localIdentifier,
                    uri = "ph://${asset.localIdentifier}",
                    dateAdded = asset.creationDate?.timeIntervalSince1970?.toLong() ?: 0L,
                    width = asset.pixelWidth.toInt(),
                    height = asset.pixelHeight.toInt(),
                ),
            )
        }

        images
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getImageBytes(id: String): ByteArray = withContext(Dispatchers.IO) {
        val fetchResult = PHAsset.fetchAssetsWithLocalIdentifiers(
            identifiers = listOf(id),
            options = null,
        )
        val asset = fetchResult.firstObject() as? PHAsset
            ?: throw IllegalStateException("PHAsset not found for id: $id")

        suspendCancellableCoroutine { continuation ->
            val options = PHImageRequestOptions().apply {
                synchronous = false
                version = PHImageRequestOptionsVersionCurrent
                deliveryMode = PHImageRequestOptionsDeliveryModeHighQualityFormat
            }

            PHImageManager.defaultManager().requestImageDataForAsset(
                asset = asset,
                options = options,
                resultHandler = { data: NSData?, _, _, _ ->
                    if (data != null) {
                        val bytes = ByteArray(data.length.toInt())
                        bytes.usePinned { pinned ->
                            platform.posix.memcpy(
                                pinned.addressOf(0),
                                data.bytes,
                                data.length,
                            )
                        }
                        continuation.resume(bytes)
                    } else {
                        continuation.resumeWithException(
                            IllegalStateException("Failed to load image data for id: $id"),
                        )
                    }
                },
            )
        }
    }
}
