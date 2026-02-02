package team.aliens.dms.kmp.feature.profile.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.image.GetGalleryImagesUseCase
import team.aliens.dms.kmp.core.model.image.GalleryImageModel

internal class SelectProfileViewModel(
    private val getGalleryImagesUseCase: GetGalleryImagesUseCase,
) : BaseViewModel<SelectProfileState, SelectProfileSideEffect>(SelectProfileState()) {

    internal fun loadImagesIfNeeded() {
        if (state.value.imageList.isNotEmpty()) return
        loadGalleryImages()
    }

    internal fun loadGalleryImages() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { state.value.copy(isLoading = true) }
            getGalleryImagesUseCase(
                page = 0,
                pageSize = 100,
            ).onSuccess { images ->
                setState { state.value.copy(imageList = images, isLoading = false) }
            }.onFailure {
                setState { state.value.copy(isLoading = false) }
                postSideEffect(SelectProfileSideEffect.ShowError("이미지를 불러올 수 없습니다"))
            }
        }
    }

    internal fun selectImage(id: String) {
        with(state.value) {
            val isSelected = selectedId == id && selectedId.isNotBlank()
            val newSelectedId = if (isSelected) "" else id
            val selectedImage = imageList.find { it.id == newSelectedId }
            setState {
                state.value.copy(
                    selectedId = newSelectedId,
                    selectedUri = selectedImage?.uri ?: "",
                    buttonEnabled = !isSelected,
                )
            }
        }
    }

    internal fun confirmSelection() {
        val selectedId = state.value.selectedId
        if (selectedId.isNotBlank()) {
            postSideEffect(SelectProfileSideEffect.ImageSelected(selectedId))
        }
    }
}

internal data class SelectProfileState(
    val selectedUri: String = "",
    val selectedId: String = "",
    val buttonEnabled: Boolean = false,
    val imageList: List<GalleryImageModel> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface SelectProfileSideEffect {
    data class ImageSelected(val id: String) : SelectProfileSideEffect
    data class ShowError(val message: String) : SelectProfileSideEffect
}
