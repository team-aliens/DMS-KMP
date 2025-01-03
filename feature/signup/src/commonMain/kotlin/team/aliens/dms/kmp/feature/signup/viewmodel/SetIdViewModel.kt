package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class SetIdViewModel :
    BaseViewModel<SetIdState, SetIdSideEffect>(SetIdState.getDefaultState()) {

    internal fun setId(id: String) {
        setState { state.value.copy(id = id) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val id = state.value.id
        state.value.copy(buttonEnabled = id.isNotEmpty())
    }

    internal fun onNextClick() {
        postSideEffect(
            SetIdSideEffect.MoveToSetPassword(
                id = "",
            ),
        )
    }
}

data class SetIdState(
    val id: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = SetIdState(
            id = "",
            buttonEnabled = false,
        )
    }
}

sealed interface SetIdSideEffect {
    data class MoveToSetPassword(
        val id: String,
    ) : SetIdSideEffect
}
