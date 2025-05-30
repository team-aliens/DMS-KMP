package tema.aliens.dms.kmp.feature.remain

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainsApplicationTimeUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainsOptionsUseCase
import team.aliens.dms.kmp.core.domain.usecase.remains.UpdateRemainsOptionUseCase
import team.aliens.dms.kmp.core.model.remains.RemainsApplicationTimeModel
import team.aliens.dms.kmp.core.model.remains.RemainsOptionModel

internal class RemainApplicationViewModel(
    private val getRemainsOptionsUseCase: GetRemainsOptionsUseCase,
    private val getRemainsApplicationTimeUseCase: GetRemainsApplicationTimeUseCase,
    private val updateRemainsOptionUseCase: UpdateRemainsOptionUseCase,
) : BaseViewModel<RemainApplicationState, RemainApplicationSideEffect>(RemainApplicationState()) {

    init {
        getRemainsOptions()
        getRemainsApplicationTime()
    }

    private fun getRemainsOptions() {
        viewModelScope.launch {
            getRemainsOptionsUseCase().onSuccess { remainsOptions ->
                val selectRemainsOptionId = remainsOptions.find { it.isApplied }?.id ?: ""
                setState {
                    state.value.copy(
                        remainsOptions = remainsOptions,
                        selectRemainsOptionId = selectRemainsOptionId,
                    )
                }
            }
        }
    }

    private fun getRemainsApplicationTime() {
        viewModelScope.launch {
            getRemainsApplicationTimeUseCase().onSuccess {
                setState { state.value.copy(remainsApplicationTime = it) }
            }
        }
    }

    internal fun setSelectRemainsOption(remainsOptionId: String) {
        setState { state.value.copy(selectRemainsOptionId = remainsOptionId) }
    }

    internal fun changeRemainsOption() {
        viewModelScope.launch {
            val remainOptionId = state.value.selectRemainsOptionId
            updateRemainsOptionUseCase(remainOptionId = remainOptionId).onSuccess {
                val remainsOptions = state.value.remainsOptions.map { remainsOption ->
                    if (remainsOption.id == state.value.selectRemainsOptionId) remainsOption.copy(
                        isApplied = true,
                    ) else remainsOption.copy(isApplied = false)
                }
                setState { state.value.copy(remainsOptions = remainsOptions) }
            }
        }
    }
}

internal data class RemainApplicationState(
    val remainsOptions: List<RemainsOptionModel> = emptyList(),
    val selectRemainsOptionId: String = "",
    val remainsApplicationTime: RemainsApplicationTimeModel = RemainsApplicationTimeModel(),
)

internal sealed interface RemainApplicationSideEffect
