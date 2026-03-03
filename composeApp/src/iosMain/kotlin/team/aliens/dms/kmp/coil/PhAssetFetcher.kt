package team.aliens.dms.kmp.coil

import coil3.ImageLoader
import coil3.Uri
import coil3.decode.DataSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.suspendCancellableCoroutine
import okio.Buffer
import platform.Foundation.NSData
import platform.Photos.PHAsset
import platform.Photos.PHImageManager
import platform.Photos.PHImageRequestOptions
import platform.Photos.PHImageRequestOptionsDeliveryModeHighQualityFormat
import platform.Photos.PHImageRequestOptionsResizeModeFast
import platform.Photos.PHImageRequestOptionsVersionCurrent
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class PhAssetFetcher(
    private val localIdentifier: String,
) : Fetcher {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun fetch(): FetchResult {
        val fetchResult = PHAsset.fetchAssetsWithLocalIdentifiers(
            identifiers = listOf(localIdentifier),
            options = null,
        )
        val asset = fetchResult.firstObject() as? PHAsset
            ?: throw IllegalStateException("PHAsset not found: $localIdentifier")

        val bytes = suspendCancellableCoroutine { continuation ->
            val options = PHImageRequestOptions().apply {
                synchronous = false
                version = PHImageRequestOptionsVersionCurrent
                deliveryMode = PHImageRequestOptionsDeliveryModeHighQualityFormat
                resizeMode = PHImageRequestOptionsResizeModeFast
                networkAccessAllowed = true
            }

            val requestId = PHImageManager.defaultManager().requestImageDataForAsset(
                asset = asset,
                options = options,
                resultHandler = { data: NSData?, _, _, _ ->
                    if (data != null) {
                        val byteArray = ByteArray(data.length.toInt())
                        byteArray.usePinned { pinned ->
                            platform.posix.memcpy(
                                pinned.addressOf(0),
                                data.bytes,
                                data.length,
                            )
                        }
                        continuation.resume(byteArray)
                    } else {
                        continuation.resumeWithException(
                            IllegalStateException("Failed to load image: $localIdentifier"),
                        )
                    }
                },
            )

            continuation.invokeOnCancellation {
                PHImageManager.defaultManager().cancelImageRequest(requestId)
            }
        }

        return SourceFetchResult(
            source = okio.FileSystem.SYSTEM_TEMPORARY_DIRECTORY.let {
                coil3.decode.ImageSource(
                    source = Buffer().apply { write(bytes) },
                    fileSystem = okio.FileSystem.SYSTEM,
                )
            },
            mimeType = null,
            dataSource = DataSource.DISK,
        )
    }

    class Factory : Fetcher.Factory<Uri> {
        override fun create(data: Uri, options: Options, imageLoader: ImageLoader): Fetcher? {
            if (data.scheme != "ph") return null
            val localIdentifier = data.toString().removePrefix("ph://")
            return PhAssetFetcher(localIdentifier)
        }
    }
}