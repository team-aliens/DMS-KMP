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