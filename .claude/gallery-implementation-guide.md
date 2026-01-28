# DMS-KMP 커스텀 갤러리 - 데이터/도메인 레이어 구현 가이드

## 작업 범위

- feature 모듈 제외, 데이터를 가져와서 가공하는 레이어만 구현
- 서버 API 없음 -> 로컬 디바이스 사진 접근 + 이미지 크롭 가공까지만

## 아키텍처 흐름

```
[Domain Layer]                    [Data Layer]                [Media Module (Platform)]
GetGalleryImagesUseCase  ->  ImageRepository(interface)  ->  LocalImageDataSource
GetImageBytesUseCase     ->  ImageRepositoryImpl         ->  ImageCropper
CropImageUseCase         ->
                                                         Android: MediaStore/Bitmap
                                                         iOS: PHAsset/UIImage
```

---

## 구현 순서

### 1단계: 의존성 추가

#### 파일: `gradle/libs.versions.toml`

[versions] 섹션에 추가:
```toml
moko-permissions = "0.18.0"
telephoto = "0.14.1"
```

[libraries] 섹션에 추가:
```toml
moko-permissions-compose = { module = "dev.icerock.moko:permissions-compose", version.ref = "moko-permissions" }
telephoto-zoomable = { module = "me.saket.telephoto:zoomable", version.ref = "telephoto" }
```

#### 파일: `settings.gradle.kts`

include 블록에 추가 (core 그룹):
```kotlin
include(":core:media")
```

---

### 2단계: core/media 모듈 생성

#### 파일: `core/media/build.gradle.kts`

```kotlin
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import team.aliens.dms.kmp.buildsrc.ProjectProperties
import team.aliens.dms.kmp.buildsrc.Versions

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ktlint)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "media"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)

            implementation(projects.core.model)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "team.aliens.dms.kmp.core.media"
    compileSdk = ProjectProperties.COMPILE_SDK
    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }
}
```

#### 파일: `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/LocalImageDataSource.kt`

```kotlin
package team.aliens.dms.kmp.core.media

import team.aliens.dms.kmp.core.model.image.GalleryImageModel

interface LocalImageDataSource {
    suspend fun getImages(page: Int, pageSize: Int): List<GalleryImageModel>
    suspend fun getImageBytes(id: String): ByteArray
}
```

#### 파일: `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/ImageCropper.kt`

```kotlin
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
```

#### 파일: `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/di/MediaModule.kt`

```kotlin
package team.aliens.dms.kmp.core.media.di

import org.koin.dsl.module

val mediaModule = module {
    includes(platformMediaModule)
}
```

#### 파일: `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.kt`

```kotlin
package team.aliens.dms.kmp.core.media.di

import org.koin.core.module.Module

expect val platformMediaModule: Module
```

---

### 3단계: Android 플랫폼 구현

#### 파일: `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/AndroidLocalImageDataSource.kt`

```kotlin
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
}
```

#### 파일: `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/AndroidImageCropper.kt`

```kotlin
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
```

#### 파일: `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.android.kt`

```kotlin
package team.aliens.dms.kmp.core.media.di

import android.content.Context
import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.media.AndroidImageCropper
import team.aliens.dms.kmp.core.media.AndroidLocalImageDataSource
import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.LocalImageDataSource

actual val platformMediaModule: Module = module {
    single<LocalImageDataSource> {
        AndroidLocalImageDataSource(
            contentResolver = get<Context>().contentResolver,
        )
    }
    single<ImageCropper> { AndroidImageCropper() }
}
```

---

### 4단계: iOS 플랫폼 구현

#### 파일: `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/IosLocalImageDataSource.kt`

```kotlin
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
```

#### 파일: `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/IosImageCropper.kt`

```kotlin
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
```

#### 파일: `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.ios.kt`

```kotlin
package team.aliens.dms.kmp.core.media.di

import org.koin.core.module.Module
import org.koin.dsl.module
import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.IosImageCropper
import team.aliens.dms.kmp.core.media.IosLocalImageDataSource
import team.aliens.dms.kmp.core.media.LocalImageDataSource

actual val platformMediaModule: Module = module {
    single<LocalImageDataSource> { IosLocalImageDataSource() }
    single<ImageCropper> { IosImageCropper() }
}
```

#### 파일: `core/media/src/jvmMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.jvm.kt`

> JVM 타겟이 있으므로 stub 구현 필요:

```kotlin
package team.aliens.dms.kmp.core.media.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformMediaModule: Module = module {
    // JVM은 갤러리 접근 불필요 (Android/iOS만 사용)
}
```

---

### 5단계: core/model에 GalleryImageModel 추가

#### 파일: `core/model/src/commonMain/kotlin/team/aliens/dms/kmp/core/model/image/GalleryImageModel.kt`

```kotlin
package team.aliens.dms.kmp.core.model.image

data class GalleryImageModel(
    val id: String,
    val uri: String,
    val dateAdded: Long,
    val width: Int,
    val height: Int,
)
```

#### 파일: `core/model/src/commonMain/kotlin/team/aliens/dms/kmp/core/model/image/CropRect.kt`

```kotlin
package team.aliens.dms.kmp.core.model.image

data class CropRect(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
)
```

---

### 6단계: core/data에 ImageRepository 추가

#### 파일: `core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/image/repository/ImageRepository.kt`

```kotlin
package team.aliens.dms.kmp.core.data.image.repository

import team.aliens.dms.kmp.core.model.image.CropRect
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

interface ImageRepository {
    suspend fun getGalleryImages(page: Int, pageSize: Int): Result<List<GalleryImageModel>>
    suspend fun getImageBytes(id: String): Result<ByteArray>
    suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray>
}
```

#### 파일: `core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/image/repository/ImageRepositoryImpl.kt`

```kotlin
package team.aliens.dms.kmp.core.data.image.repository

import team.aliens.dms.kmp.core.media.ImageCropper
import team.aliens.dms.kmp.core.media.LocalImageDataSource
import team.aliens.dms.kmp.core.model.image.CropRect
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

internal class ImageRepositoryImpl(
    private val localImageDataSource: LocalImageDataSource,
    private val imageCropper: ImageCropper,
) : ImageRepository {

    override suspend fun getGalleryImages(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>> = runCatching {
        localImageDataSource.getImages(page = page, pageSize = pageSize)
    }

    override suspend fun getImageBytes(id: String): Result<ByteArray> = runCatching {
        localImageDataSource.getImageBytes(id = id)
    }

    override suspend fun cropImage(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray> = runCatching {
        imageCropper.cropImage(
            imageBytes = imageBytes,
            cropRect = cropRect,
            outputWidth = outputWidth,
            outputHeight = outputHeight,
        )
    }
}
```

> **주의**: `core/data/build.gradle.kts`에 `implementation(projects.core.media)` 의존성 추가 필요:
>
> ```kotlin
> commonMain.dependencies {
>     implementation(projects.core.model)
>     implementation(projects.core.network)
>     implementation(projects.core.datastore)
>     implementation(projects.core.database)
>     implementation(projects.core.util)
>     implementation(projects.core.media)  // 추가
>
>     implementation(libs.kotlinx.datetime)
>     implementation(libs.koin.core)
> }
> ```

#### 파일: `core/data/di/RepositoryModule.kt` (수정)

기존 파일에 추가:

```kotlin
// import 추가
import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.data.image.repository.ImageRepositoryImpl

// module 블록 내부에 추가
singleOf(::ImageRepositoryImpl) { bind<ImageRepository>() }
```

---

### 7단계: core/domain에 UseCases 추가

#### 파일: `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/GetGalleryImagesUseCase.kt`

```kotlin
package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

class GetGalleryImagesUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>> =
        imageRepository.getGalleryImages(
            page = page,
            pageSize = pageSize,
        )
}
```

#### 파일: `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/GetImageBytesUseCase.kt`

```kotlin
package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository

class GetImageBytesUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(id: String): Result<ByteArray> =
        imageRepository.getImageBytes(id = id)
}
```

#### 파일: `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/CropImageUseCase.kt`

```kotlin
package team.aliens.dms.kmp.core.domain.usecase.image

import team.aliens.dms.kmp.core.data.image.repository.ImageRepository
import team.aliens.dms.kmp.core.model.image.CropRect

class CropImageUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        imageBytes: ByteArray,
        cropRect: CropRect,
        outputWidth: Int,
        outputHeight: Int,
    ): Result<ByteArray> =
        imageRepository.cropImage(
            imageBytes = imageBytes,
            cropRect = cropRect,
            outputWidth = outputWidth,
            outputHeight = outputHeight,
        )
}
```

#### 파일: `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/di/ImageModule.kt`

```kotlin
package team.aliens.dms.kmp.core.domain.usecase.image.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.domain.usecase.image.CropImageUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetGalleryImagesUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetImageBytesUseCase

internal val imageModule = module {
    singleOf(::GetGalleryImagesUseCase)
    singleOf(::GetImageBytesUseCase)
    singleOf(::CropImageUseCase)
}
```

#### 파일: `core/domain/di/DomainModule.kt` (수정)

기존 파일에 추가:

```kotlin
// import 추가
import team.aliens.dms.kmp.core.domain.usecase.image.di.imageModule

// includes 블록에 추가
includes(
    authModule,
    mealModule,
    noticeModule,
    studentModule,
    remainsModule,
    votesModule,
    pointsModule,
    schoolsModule,
    onboardingModule,
    notificationModule,
    userModule,
    imageModule,  // 추가
)
```

---

### 8단계: Koin DI 등록 (앱 진입점)

앱의 최상위 Koin 모듈 설정에 `mediaModule`이 포함되어 있는지 확인.
`composeApp`의 Koin 초기화 부분에서 `mediaModule`을 등록해야 함.

기존 패턴을 따라 `repositoryModule`과 `domainModule`은 이미 등록되어 있으므로,
`mediaModule`만 추가로 includes하면 됨.

> `mediaModule` -> `platformMediaModule` (expect/actual)로 플랫폼별 DataSource/Cropper 주입
> `repositoryModule` -> `ImageRepositoryImpl` (DataSource/Cropper 의존)
> `domainModule` -> `imageModule` -> UseCases (ImageRepository 의존)

---

## 파일 생성/수정 체크리스트

| # | 작업 | 파일 경로 |
|---|------|-----------|
| 1 | 수정 | `gradle/libs.versions.toml` |
| 2 | 수정 | `settings.gradle.kts` |
| 3 | 생성 | `core/media/build.gradle.kts` |
| 4 | 생성 | `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/LocalImageDataSource.kt` |
| 5 | 생성 | `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/ImageCropper.kt` |
| 6 | 생성 | `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/di/MediaModule.kt` |
| 7 | 생성 | `core/media/src/commonMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.kt` |
| 8 | 생성 | `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/AndroidLocalImageDataSource.kt` |
| 9 | 생성 | `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/AndroidImageCropper.kt` |
| 10 | 생성 | `core/media/src/androidMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.android.kt` |
| 11 | 생성 | `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/IosLocalImageDataSource.kt` |
| 12 | 생성 | `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/IosImageCropper.kt` |
| 13 | 생성 | `core/media/src/iosMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.ios.kt` |
| 14 | 생성 | `core/media/src/jvmMain/kotlin/team/aliens/dms/kmp/core/media/di/PlatformMediaModule.jvm.kt` |
| 15 | 생성 | `core/model/src/commonMain/kotlin/team/aliens/dms/kmp/core/model/image/GalleryImageModel.kt` |
| 16 | 생성 | `core/model/src/commonMain/kotlin/team/aliens/dms/kmp/core/model/image/CropRect.kt` |
| 17 | 생성 | `core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/image/repository/ImageRepository.kt` |
| 18 | 생성 | `core/data/src/commonMain/kotlin/team/aliens/dms/kmp/core/data/image/repository/ImageRepositoryImpl.kt` |
| 19 | 수정 | `core/data/build.gradle.kts` (core.media 의존성 추가) |
| 20 | 수정 | `core/data/di/RepositoryModule.kt` (ImageRepository 등록) |
| 21 | 생성 | `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/GetGalleryImagesUseCase.kt` |
| 22 | 생성 | `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/GetImageBytesUseCase.kt` |
| 23 | 생성 | `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/CropImageUseCase.kt` |
| 24 | 생성 | `core/domain/src/commonMain/kotlin/team/aliens/dms/kmp/core/domain/usecase/image/di/ImageModule.kt` |
| 25 | 수정 | `core/domain/di/DomainModule.kt` (imageModule 추가) |

## 프로젝트 패턴 준수 사항

- Repository는 `Result<T>` 반환 (`runCatching` 사용)
- Impl 클래스는 `internal` 접근 제한자
- UseCase는 `suspend operator fun invoke(...)` 패턴
- Koin: `singleOf(::XxxImpl) { bind<Xxx>() }` (Repository), `singleOf(::XxxUseCase)` (UseCase)
- DI 모듈은 `internal val xxxModule = module { ... }` (domain), `val xxxModule = module { ... }` (data/media)
- 플랫폼별 구현: `expect`/`actual` 패턴 (Module 타입)
- build.gradle.kts: 모든 core 모듈은 동일한 구조 (androidTarget, jvm, ios 3종)
- 패키지: `team.aliens.dms.kmp.core.{module}.{feature}`

## 검증 단계

1. Gradle sync 성공: `./gradlew :core:media:compileKotlinMetadata`
2. Android 빌드: `./gradlew :core:media:compileDebugKotlinAndroid`
3. 전체 data/domain 빌드: `./gradlew :core:data:compileKotlinMetadata :core:domain:compileKotlinMetadata`
4. 삭제된 참조 확인: `grep -r "SignUpInfoBanner\|ResetPasswordInfoContent" --include="*.kt"`
5. Koin 그래프: `mediaModule` -> `platformMediaModule` -> DataSource/Cropper -> `ImageRepositoryImpl` -> UseCases