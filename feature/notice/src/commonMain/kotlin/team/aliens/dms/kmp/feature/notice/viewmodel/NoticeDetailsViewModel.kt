package team.aliens.dms.kmp.feature.notice.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class NoticeDetailsViewModel : BaseViewModel<NoticeDetailsState,NoticeDetailsSideEffect>(NoticeDetailsState.getDefaultState()) {

}

internal data class NoticeDetailsState(
    val s: String,
) {
    companion object {
        fun getDefaultState() = NoticeDetailsState(
            s = ""
        )
    }
}

internal sealed interface NoticeDetailsSideEffect
