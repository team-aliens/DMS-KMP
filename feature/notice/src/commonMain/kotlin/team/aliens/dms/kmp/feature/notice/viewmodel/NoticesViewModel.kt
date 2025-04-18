package team.aliens.dms.kmp.feature.notice.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticesUseCase
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.core.model.type.OrderType

internal class NoticesViewModel(
    private val getNoticesUseCase: GetNoticesUseCase,
) : BaseViewModel<NoticesState, NoticesSideEffect>(NoticesState.getDefaultState()) {

    init {
        getNotices()
    }

    private fun getNotices() {
        viewModelScope.launch {
            getNoticesUseCase(orderType = if (state.value.isRecent) OrderType.NEW else OrderType.OLD)
                .onFailure {
                    // TODO: 실패 예외처리
                }
                .onSuccess {
                    setState { state.value.copy(notices = it) }
                }
        }
    }

    internal fun setIsRecent() {
        val isRecent = state.value.isRecent
        setState { state.value.copy(isRecent = !isRecent) }
        getNotices()
    }
}

internal data class NoticesState(
    val isRecent: Boolean,
    val notices: List<NoticeModel>
) {
    companion object {
        fun getDefaultState() = NoticesState(
            isRecent = true,
            notices = emptyList(),
        )
    }
}

internal sealed interface NoticesSideEffect
