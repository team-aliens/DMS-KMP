package team.aliens.dms.kmp.feature.notice.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.utils.now
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticeDetailUseCase
import team.aliens.dms.kmp.core.model.notice.NoticeDetailModel
import team.aliens.dms.kmp.feature.notice.navigation.NoticeDetailRoute

internal class NoticeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) :
    BaseViewModel<NoticeDetailsState, NoticeDetailsSideEffect>(NoticeDetailsState.getDefaultState()) {

    private val route = savedStateHandle.toRoute<NoticeDetailRoute>()

    init {
        getNoticeDetail()
    }

    private fun getNoticeDetail() {
        viewModelScope.launch {
            getNoticeDetailUseCase(noticeId = route.noticeId)
                .onSuccess {
                    setState { state.value.copy(notice = it) }
                }
        }
    }
}

internal data class NoticeDetailsState(
    val notice: NoticeDetailModel,
) {
    companion object {
        fun getDefaultState() = NoticeDetailsState(
            notice = NoticeDetailModel(
                id = "",
                title = "",
                content = "",
                createdAt = now,
            ),
        )
    }
}

internal sealed interface NoticeDetailsSideEffect
