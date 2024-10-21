package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class TermsViewModel :
    BaseViewModel<TermsState, TermsSideEffect>(TermsState.getDefaultState()) {

    internal fun setButtonEnabled(buttonEnabled: Boolean) {
        setState { state.value.copy(buttonEnabled = buttonEnabled) }
    }
}

data class TermsState(
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = TermsState(
            buttonEnabled = false,
        )
    }
}

sealed interface TermsSideEffect {
    data object Success : TermsSideEffect
}
