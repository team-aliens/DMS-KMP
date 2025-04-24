package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp
import team.aliens.dms.kmp.feature.signup.ui.EMAIL_VERIFICATION_CODE_LENGTH

internal class EnterEmailVerificationCodeViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EnterEmailVerificationCodeState, EnterEmailVerificationCodeSideEffect>(
        EnterEmailVerificationCodeState.getDefaultState(),
    ) {

    private val route = savedStateHandle.toRoute<SignUpData>(
        typeMap = SignUp.Route.NavTypeMap,
    )

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
                timerFinished = timerFinished,
            )
        }
    }

    private fun setButtonEnabled() = setState {
        val emailVerificationCode = state.value.emailVerificationCode
        state.value.copy(buttonEnabled = emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH)
    }

    internal fun onNextClick() {
        postSideEffect(
            EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber(
                signUpData = route.copy(
                    authCode = state.value.emailVerificationCode,
                ),
            ),
        )
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
    data class MoveToEnterStudentNumber(val signUpData: SignUpData) :
        EnterEmailVerificationCodeSideEffect
}
