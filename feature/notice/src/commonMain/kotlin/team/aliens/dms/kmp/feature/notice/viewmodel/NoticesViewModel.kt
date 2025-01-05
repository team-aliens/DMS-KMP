package team.aliens.dms.kmp.feature.notice.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class NoticesViewModel : BaseViewModel<NoticesState, NoticesSideEffect>(NoticesState.getDefaultState()) {

    internal fun setIsRecent() {
        val isRecent = state.value.isRecent
        setState { state.value.copy(isRecent = !isRecent) }
    }
}

internal data class NoticesState(
    val isRecent: Boolean,
) {
    companion object {
        fun getDefaultState() = NoticesState(
            isRecent = true,
        )
    }
}

internal sealed interface NoticesSideEffect
