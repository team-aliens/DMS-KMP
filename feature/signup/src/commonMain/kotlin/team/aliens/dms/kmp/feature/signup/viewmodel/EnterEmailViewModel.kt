package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class EnterEmailViewModel :
    BaseViewModel<EnterEmailState, EnterEmailSideEffect>(EnterEmailState.getDefaultState()) {

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
        postSideEffect(EnterEmailSideEffect.MoveToEnterEmailVerificationCode(email = ""))
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
    data class MoveToEnterEmailVerificationCode(val email: String) : EnterEmailSideEffect
}
