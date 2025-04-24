package team.aliens.dms.kmp.feature.notice.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticeDetailUseCase
import team.aliens.dms.kmp.feature.notice.navigation.NoticeDetailRoute

internal class NoticeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) :
    BaseViewModel<NoticeDetailsState, NoticeDetailsSideEffect>(NoticeDetailsState.getDefaultState()) {
    private val route = savedStateHandle.toRoute<NoticeDetailRoute>()

    internal fun getNoticeDetail(noticeId: String) {
        viewModelScope.launch {
            getNoticeDetailUseCase(noticeId = noticeId)
        }
    }
}

internal data class NoticeDetailsState(
    val s: String,
) {
    companion object {
        fun getDefaultState() = NoticeDetailsState(
            s = "",
        )
    }
}

internal sealed interface NoticeDetailsSideEffect
