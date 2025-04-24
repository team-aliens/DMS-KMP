package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class SetIdViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<SetIdState, SetIdSideEffect>(SetIdState.getDefaultState()) {

    private val route = savedStateHandle.toRoute<SignUpData>(
        typeMap = SignUp.Route.NavTypeMap,
    )

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
                signUpData = route.copy(accountId = state.value.id),
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
        val signUpData: SignUpData,
    ) : SetIdSideEffect
}
