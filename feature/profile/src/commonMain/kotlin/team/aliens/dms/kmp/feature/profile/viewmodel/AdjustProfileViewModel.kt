package team.aliens.dms.kmp.feature.profile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.file.FetchPresignedUrlUseCase
import team.aliens.dms.kmp.core.domain.usecase.file.UploadFileUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.CropImageUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetImageBytesUseCase
import team.aliens.dms.kmp.core.domain.usecase.image.GetImageUriUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.EditProfileUseCase
import team.aliens.dms.kmp.core.model.image.CropRect
import team.aliens.dms.kmp.feature.profile.navigation.AdjustProfileRoute
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class AdjustProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val getImageUriUseCase: GetImageUriUseCase,
    private val getImageBytesUseCase: GetImageBytesUseCase,
    private val cropImageUseCase: CropImageUseCase,
    private val fetchPresignedUrlUseCase: FetchPresignedUrlUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
) : BaseViewModel<AdjustProfileState, AdjustProfileSideEffect>(AdjustProfileState()) {

    private val route = savedStateHandle.toRoute<AdjustProfileRoute>()

    init {
        setState { state.value.copy(imageId = route.imageId) }
        loadImageUri()
    }

    private fun loadImageUri() {
        viewModelScope.launch(Dispatchers.IO) {
            getImageUriUseCase(state.value.imageId).onSuccess { uri ->
                setState { state.value.copy(imageUri = uri) }
            }
        }
    }

    fun updateZoomState(scale: Float, offsetX: Float, offsetY: Float) {
        setState {
            state.value.copy(
                zoomScale = scale,
                zoomOffsetX = offsetX,
                zoomOffsetY = offsetY,
            )
        }
    }

    fun uploadCroppedProfile(outputSize: Int = 512) {
        viewModelScope.launch(Dispatchers.IO) {
            setState { state.value.copy(isLoading = true) }

            val cropRect = calculateCropRect()

            getImageBytesUseCase(state.value.imageId)
                .mapCatching { originalBytes ->
                    cropImageUseCase(originalBytes, cropRect, outputSize, outputSize).getOrThrow()
                }
                .mapCatching { croppedBytes ->
                    @OptIn(ExperimentalUuidApi::class)
                    val fileName = "profile_${Uuid.random()}.jpg"
                    val presignedUrl = fetchPresignedUrlUseCase(fileName).getOrThrow()
                    uploadFileUseCase(presignedUrl, croppedBytes).getOrThrow()
                }
                .mapCatching { fileUrl ->
                    editProfileUseCase(fileUrl).getOrThrow()
                }
                .onSuccess {
                    setState { state.value.copy(isLoading = false) }
                    postSideEffect(AdjustProfileSideEffect.ProfileImageSet)
                }
                .onFailure {
                    setState { state.value.copy(isLoading = false) }
                    postSideEffect(AdjustProfileSideEffect.ProfileImageBadRequest)
                }
        }
    }

    private fun calculateCropRect(): CropRect {
        val scale = state.value.zoomScale
        val offsetX = state.value.zoomOffsetX
        val offsetY = state.value.zoomOffsetY

        val visibleWidth = (1f / scale).coerceIn(0f, 1f)
        val visibleHeight = (1f / scale).coerceIn(0f, 1f)

        val normalizedX = (-offsetX / scale).coerceIn(0f, 1f - visibleWidth)
        val normalizedY = (-offsetY / scale).coerceIn(0f, 1f - visibleHeight)

        return CropRect(normalizedX, normalizedY, visibleWidth, visibleHeight)
    }
}

internal data class AdjustProfileState(
    val imageId: String = "",
    val imageUri: String = "",
    val isLoading: Boolean = false,
    val zoomScale: Float = 1f,
    val zoomOffsetX: Float = 0f,
    val zoomOffsetY: Float = 0f,
)

internal sealed interface AdjustProfileSideEffect {
    data object ProfileImageSet : AdjustProfileSideEffect
    data object ProfileImageBadRequest : AdjustProfileSideEffect
}
