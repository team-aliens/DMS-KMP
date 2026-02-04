# Feature/Profile 갤러리 이미지 선택 KMP 구현 가이드

## 목표
기존 Android-only 코드(Hilt, MediaStore 직접 접근)를 KMP 패턴으로 리팩토링하여 Android/iOS 모두 지원

## 전제 조건 (이미 구현됨)
- `core/media`: `LocalImageDataSource` + 플랫폼별 구현체
- `core/data`: `ImageRepository` + `ImageRepositoryImpl`
- `core/domain`: `GetGalleryImagesUseCase` + `imageModule`
- `core/model`: `GalleryImageModel`

## 참조 API

### LocalImageDataSource (core/media)
```kotlin
interface LocalImageDataSource {
    suspend fun getImages(page: Int, pageSize: Int): List<GalleryImageModel>
    suspend fun getImageBytes(id: String): ByteArray
}
```

### GetGalleryImagesUseCase (core/domain)
```kotlin
class GetGalleryImagesUseCase(
    private val imageRepository: ImageRepository,
) {
    suspend operator fun invoke(
        page: Int,
        pageSize: Int,
    ): Result<List<GalleryImageModel>>
}
```

---

## 구현 순서

### 1단계: SelectProfileViewModel 리팩토링

**파일:** `feature/profile/src/commonMain/kotlin/team/aliens/dms/kmp/feature/profile/viewmodel/SelectProfileViewModel.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.image.GetGalleryImagesUseCase

internal class SelectProfileViewModel(
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
) : BaseViewModel<SelectProfileState, SelectProfileSideEffect>(SelectProfileState()) {

    internal fun loadImagesIfNeeded() {
        if (state.value.uriList.isNotEmpty()) return
        loadGalleryImages()
    }

    internal fun loadGalleryImages() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { state.value.copy(isLoading = true) }
            getGalleryImagesUseCase(
                page = 0,
                pageSize = 100,
            ).onSuccess { images ->
                val uriList = images.map { it.uri }
                setState { state.value.copy(uriList = uriList, isLoading = false) }
            }.onFailure {
                setState { state.value.copy(isLoading = false) }
                postSideEffect(SelectProfileSideEffect.ShowError("이미지를 불러올 수 없습니다"))
            }
        }
    }

    internal fun selectImage(uri: String) {
        with(state.value) {
            val isSelected = selectedUri == uri && selectedUri.isNotBlank()
            val newSelectedUri = if (isSelected) "" else uri
            setState {
                state.value.copy(
                    selectedUri = newSelectedUri,
                    buttonEnabled = !isSelected,
                )
            }
        }
    }

    internal fun confirmSelection() {
        val selectedUri = state.value.selectedUri
        if (selectedUri.isNotBlank()) {
            postSideEffect(SelectProfileSideEffect.ImageSelected(selectedUri))
        }
    }
}

internal data class SelectProfileState(
    val selectedUri: String = "",
    val buttonEnabled: Boolean = false,
    val uriList: List<String> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface SelectProfileSideEffect {
    data class ImageSelected(val uri: String) : SelectProfileSideEffect
    data class ShowError(val message: String) : SelectProfileSideEffect
}
```

---

### 2단계: ProfileModule Koin DI 생성

**파일:** `feature/profile/src/commonMain/kotlin/team/aliens/dms/kmp/feature/profile/di/ProfileModule.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileViewModel

val profileModule = module {
    viewModelOf(::SelectProfileViewModel)
}
```

---

### 3단계: 권한 처리 expect/actual 분리 (moko-permissions 사용)

#### commonMain expect 선언

**파일:** `feature/profile/src/commonMain/kotlin/team/aliens/dms/kmp/feature/profile/ui/GalleryPermission.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.runtime.Composable

@Composable
expect fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
)
```

#### androidMain actual 구현

**파일:** `feature/profile/src/androidMain/kotlin/team/aliens/dms/kmp/feature/profile/ui/GalleryPermission.android.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsController

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    val permissionsController = rememberPermissionsController()
    val permission = if (android.os.Build.VERSION.SDK_INT >= 33) {
        Permission.READ_MEDIA_IMAGES
    } else {
        Permission.READ_EXTERNAL_STORAGE
    }

    BindEffect(permissionsController)

    LaunchedEffect(Unit) {
        try {
            permissionsController.providePermission(permission)
            onPermissionGranted()
        } catch (e: Exception) {
            onPermissionDenied()
        }
    }

    content()
}
```

#### iosMain actual 구현

**파일:** `feature/profile/src/iosMain/kotlin/team/aliens/dms/kmp/feature/profile/ui/GalleryPermission.ios.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsController

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    val permissionsController = rememberPermissionsController()
    val permission = Permission.GALLERY

    BindEffect(permissionsController)

    LaunchedEffect(Unit) {
        try {
            permissionsController.providePermission(permission)
            onPermissionGranted()
        } catch (e: Exception) {
            onPermissionDenied()
        }
    }
    
    content()
}
```

#### jvmMain stub 구현

**파일:** `feature/profile/src/jvmMain/kotlin/team/aliens/dms/kmp/feature/profile/ui/GalleryPermission.jvm.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    // JVM desktop은 갤러리 권한 불필요
    LaunchedEffect(Unit) {
        onPermissionGranted()
    }
    content()
}
```

---

### 4단계: SelectProfileScreen KMP 패턴으로 리팩토링

**파일:** `feature/profile/src/commonMain/kotlin/team/aliens/dms/kmp/feature/profile/ui/SelectProfileScreen.kt`

```kotlin
package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileSideEffect
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileState
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileViewModel

@Composable
internal fun SelectProfile(
    onBackPressed: () -> Unit,
    onImageSelected: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SelectProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SelectProfileSideEffect.ImageSelected -> onImageSelected(effect.uri)
                is SelectProfileSideEffect.ShowError -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    effect.message,
                )
            }
        }
    }

    RequestGalleryPermission(
        onPermissionGranted = { viewModel.loadImagesIfNeeded() },
        onPermissionDenied = { onShowSnackBar(DmsSnackBarType.ERROR, "갤러리 권한이 필요합니다") },
    ) {
        SelectProfileScreen(
            state = state,
            onBackPressed = onBackPressed,
            onImageClick = viewModel::selectImage,
            onConfirmClick = viewModel::confirmSelection,
        )
    }
}

@Composable
private fun SelectProfileScreen(
    state: SelectProfileState,
    onBackPressed: () -> Unit,
    onImageClick: (String) -> Unit,
    onConfirmClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DmsTopAppBar(
                title = "프로필 이미지 선택",
                onBackPressed = onBackPressed,
            )
        },
        bottomBar = {
            DmsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "선택 완료",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                enabled = state.buttonEnabled,
                onClick = onConfirmClick,
            )
        },
        containerColor = DmsTheme.colors.surfaceTint,
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = state.uriList,
                key = { it },
            ) { uri ->
                ImageItem(
                    uri = uri,
                    isSelected = state.selectedUri == uri,
                    onImageClick = { onImageClick(uri) },
                )
            }
        }
    }
}

@Composable
private fun ImageItem(
    uri: String,
    isSelected: Boolean,
    onImageClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onImageClick),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(32.dp)
                    .background(DmsTheme.colors.primary, CircleShape)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
```

---

### 5단계: build.gradle.kts 의존성 추가

#### feature/profile/build.gradle.kts

```kotlin
kotlin {
    // ... 기존 설정 ...

    sourceSets {
        commonMain.dependencies {
            // 기존 의존성...
            implementation(projects.core.common)
            implementation(projects.core.designSystem)
            implementation(projects.core.model)
            implementation(projects.core.domain)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.coil.compose)
            implementation(libs.moko.permissions.compose) // 추가
        }
        // androidMain.dependencies 블록은 필요 없음
    }
}
```

---

## 파일 생성/수정 체크리스트

| # | 파일 | 작업 |
|---|------|------|
| 1 | `feature/profile/.../viewmodel/SelectProfileViewModel.kt` | **전체 교체** |
| 2 | `feature/profile/.../di/ProfileModule.kt` | **신규 생성** |
| 3 | `feature/profile/.../ui/GalleryPermission.kt` (commonMain) | **신규 생성** |
| 4 | `feature/profile/.../ui/GalleryPermission.android.kt` (androidMain) | **신규 생성** (moko-permissions 사용) |
| 5 | `feature/profile/.../ui/GalleryPermission.ios.kt` (iosMain) | **신규 생성** (moko-permissions 사용) |
| 6 | `feature/profile/.../ui/GalleryPermission.jvm.kt` (jvmMain) | **신규 생성** |
| 7 | `feature/profile/.../ui/SelectProfileScreen.kt` (commonMain) | **전체 교체** |
| 8 | `feature/profile/build.gradle.kts` | commonMain 의존성 추가 |

---

## 아키텍처 흐름

```
[Feature/Profile]              [Domain]                    [Data]                      [Media]
SelectProfileViewModel  →  GetGalleryImagesUseCase  →  ImageRepository  →  LocalImageDataSource
        ↓                                                     ↓                         ↓
   state.uriList                                    ImageRepositoryImpl        Android: MediaStore
                                                                               iOS: PHAsset
```

---

## 검증

```bash
./gradlew :feature:profile:compileKotlinMetadata
./gradlew :feature:profile:compileDebugKotlinAndroid
```

---

## 앱 Koin 등록

`composeApp` 또는 앱 진입점의 Koin 설정에 `profileModule` 포함 필요:

```kotlin
startKoin {
    modules(
        // ... 기존 모듈들 ...
        profileModule,
    )
}
```