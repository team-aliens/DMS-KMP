package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.feature.signup.ui.EMAIL_VERIFICATION_CODE_LENGTH

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

    internal fun setTimerFinished(timerFinished: Boolean) {
        setState {
            state.value.copy(
                timerFinished = timerFinished
            )
        }
    }

    private fun setButtonEnabled() = setState {
        val emailVerificationCode = state.value.emailVerificationCode
        state.value.copy(buttonEnabled = emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH)
    }

    internal fun onNextClick() {
        postSideEffect(EnterEmailVerificationCodeSideEffect.MoveToSetId(authCode = ""))
    }
}

data class EnterEmailVerificationCodeState(
    val emailVerificationCode: String,
    val timerFinished: Boolean,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterEmailVerificationCodeState(
            emailVerificationCode = "",
            timerFinished = false,
            buttonEnabled = false,
        )
    }
}

sealed interface EnterEmailVerificationCodeSideEffect {
    data class MoveToSetId(val authCode: String) : EnterEmailVerificationCodeSideEffect
}
