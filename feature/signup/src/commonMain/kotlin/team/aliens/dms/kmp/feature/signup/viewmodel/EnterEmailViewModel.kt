package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterEmailViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EnterEmailState, EnterEmailSideEffect>(EnterEmailState.getDefaultState()) {

    private val route = savedStateHandle.toRoute<SignUpData>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    internal fun setEmail(email: String) {
        setState {
            state.value.copy(
                email = email,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val email = state.value.email
        state.value.copy(buttonEnabled = email.isNotEmpty())
    }

    internal fun onNextClick() {
        postSideEffect(
            EnterEmailSideEffect.MoveToEnterEmailVerificationCode(
                signUpData = route.copy(
                    email = state.value.email,
                ),
            ),
        )
    }
}

data class EnterEmailState(
    val email: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterEmailState(
            email = "",
            buttonEnabled = false,
        )
    }
}

sealed interface EnterEmailSideEffect {
    data class MoveToEnterEmailVerificationCode(val signUpData: SignUpData) : EnterEmailSideEffect
}
