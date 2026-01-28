package team.aliens.dms.kmp.core.media

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import team.aliens.dms.kmp.core.model.image.CropRect
import java.io.ByteArrayOutputStream

internal class AndroidImageCropper : ImageCropper {

    override suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): ByteArray = withContext(Dispatchers.IO) {
        val original = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            ?: throw IllegalStateException("Failed to decode image bytes")

        val x = (cropRect.x * original.width).toInt()
        val y = (cropRect.y * original.height).toInt()
        val width = (cropRect.width * original.width).toInt()
        val height = (cropRect.height * original.height).toInt()

        val cropped = Bitmap.createBitmap(original, x, y, width, height)
        val scaled = Bitmap.createScaledBitmap(cropped, outputWidth, outputHeight, true)

        val output = ByteArrayOutputStream()
        scaled.compress(Bitmap.CompressFormat.JPEG, 90, output)

        if (cropped != original) cropped.recycle()
        if (scaled != cropped) scaled.recycle()
        original.recycle()

        output.toByteArray()
    }
}
