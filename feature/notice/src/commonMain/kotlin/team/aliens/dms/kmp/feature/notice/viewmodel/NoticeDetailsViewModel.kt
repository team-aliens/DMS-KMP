package team.aliens.dms.kmp.feature.notice.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.notice.GetNoticeDetailUseCase

internal class NoticeDetailsViewModel(
    private val getNoticeDetailUseCase: GetNoticeDetailUseCase,
) :
    BaseViewModel<NoticeDetailsState, NoticeDetailsSideEffect>(NoticeDetailsState.getDefaultState()) {

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
