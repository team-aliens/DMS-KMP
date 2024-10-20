package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class EnterEmailVerificationCodeViewModel :
    BaseViewModel<EnterEmailVerificationCodeState, EnterEmailVerificationCodeSideEffect>(
        EnterEmailVerificationCodeState.getDefaultState(),
    ) {

    internal fun setEmailVerificationCode(emailVerificationCode: String) {
        setState {
            state.value.copy(
                emailVerificationCode = emailVerificationCode,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val emailVerificationCode = state.value.emailVerificationCode
        state.value.copy(buttonEnabled = emailVerificationCode.length == 6)
    }

    internal fun onNextClick() {
        postSideEffect(EnterEmailVerificationCodeSideEffect.MoveToSetId(authCode = ""))
    }
}

data class EnterEmailVerificationCodeState(
    val emailVerificationCode: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterEmailVerificationCodeState(
            emailVerificationCode = "",
            buttonEnabled = false,
        )
    }
}

sealed interface EnterEmailVerificationCodeSideEffect {
    data class MoveToSetId(val authCode: String) : EnterEmailVerificationCodeSideEffect
}
