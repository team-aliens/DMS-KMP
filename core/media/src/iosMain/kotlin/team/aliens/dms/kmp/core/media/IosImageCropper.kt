package team.aliens.dms.kmp.core.media

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGContextDrawImage
import platform.CoreGraphics.CGImageGetBitsPerComponent
import platform.CoreGraphics.CGImageGetHeight
import platform.CoreGraphics.CGImageGetWidth
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import team.aliens.dms.kmp.core.model.image.CropRect

internal class IosImageCropper : ImageCropper {

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): ByteArray = withContext(Dispatchers.IO) {
        val nsData = imageBytes.usePinned { pinned ->
            NSData.dataWithBytes(pinned.addressOf(0), imageBytes.size.toULong())
        }
        val uiImage = UIImage(data = nsData)
        val cgImage = uiImage.CGImage
            ?: throw IllegalStateException("Failed to get CGImage")

        val imageWidth = CGImageGetWidth(cgImage).toFloat()
        val imageHeight = CGImageGetHeight(cgImage).toFloat()

        val x = (cropRect.x * imageWidth).toInt()
        val y = (cropRect.y * imageHeight).toInt()
        val width = (cropRect.width * imageWidth).toInt()
        val height = (cropRect.height * imageHeight).toInt()

        val cropCGRect = CGRectMake(
            x.toDouble(),
            y.toDouble(),
            width.toDouble(),
            height.toDouble(),
        )

        val croppedCGImage = platform.CoreGraphics.CGImageCreateWithImageInRect(cgImage, cropCGRect)
            ?: throw IllegalStateException("Failed to crop image")

        val colorSpace = CGColorSpaceCreateDeviceRGB()
        val bitsPerComponent = CGImageGetBitsPerComponent(croppedCGImage)

        val context = CGBitmapContextCreate(
            data = null,
            width = outputWidth.toULong(),
            height = outputHeight.toULong(),
            bitsPerComponent = bitsPerComponent,
            bytesPerRow = 0u,
            space = colorSpace,
            bitmapInfo = platform.CoreGraphics.CGImageGetBitmapInfo(croppedCGImage),
        ) ?: throw IllegalStateException("Failed to create bitmap context")

        val drawRect = CGRectMake(0.0, 0.0, outputWidth.toDouble(), outputHeight.toDouble())
        CGContextDrawImage(context, drawRect, croppedCGImage)

        val scaledCGImage = CGBitmapContextCreateImage(context)
            ?: throw IllegalStateException("Failed to create scaled image")

        val resultImage = UIImage(cGImage = scaledCGImage)
        val jpegData = UIImageJPEGRepresentation(resultImage, 0.9)
            ?: throw IllegalStateException("Failed to convert to JPEG")

        val resultBytes = ByteArray(jpegData.length.toInt())
        resultBytes.usePinned { pinned ->
            platform.posix.memcpy(
                pinned.addressOf(0),
                jpegData.bytes,
                jpegData.length,
            )
        }

        resultBytes
    }
}
