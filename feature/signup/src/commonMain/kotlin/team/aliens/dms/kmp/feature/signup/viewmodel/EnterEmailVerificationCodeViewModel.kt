package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.model.message.TextFieldError
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.core.model.type.EmailVerificationType
import team.aliens.dms.kmp.feature.signup.model.SignUpTextFieldError
import team.aliens.dms.kmp.feature.signup.navigation.SignUp
import team.aliens.dms.kmp.feature.signup.ui.EMAIL_VERIFICATION_CODE_LENGTH

internal class EnterEmailVerificationCodeViewModel(
    savedStateHandle: SavedStateHandle,
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,
) :
    BaseViewModel<EnterEmailVerificationCodeState, EnterEmailVerificationCodeSideEffect>(
        EnterEmailVerificationCodeState(),
    ) {

    private val route = savedStateHandle.toRoute<SignUp.Route.EnterEmailVerificationCode>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    init {
        setState { state.value.copy(email = route.signUpData.email) }
        sendEmailVerificationCode()
    }

    private fun sendEmailVerificationCode() = viewModelScope.launch {
        setState { state.value.copy(textFieldError = TextFieldError.None()) }
        sendEmailVerificationCodeUseCase(
            email = route.signUpData.email,
            type = EmailVerificationType.SIGNUP,
        ).onFailure { exception ->
            postSideEffect(EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar)
            Logger.e(exception) { exception.message.toString() }
        }
    }

    internal fun resendEmailVerificationCode() = viewModelScope.launch {
        setState {
            state.value.copy(
                isResendLoading = true,
                textFieldError = TextFieldError.None(),
            )
        }
        sendEmailVerificationCodeUseCase(
            email = state.value.email,
            type = EmailVerificationType.SIGNUP,
        ).onSuccess {
            setState { state.value.copy(textFieldError = TextFieldError.None()) }
            postSideEffect(EnterEmailVerificationCodeSideEffect.ResetCountDownTimer)
        }.onFailure { exception ->
            postSideEffect(EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar)
            Logger.e(exception) { exception.message.toString() }
        }
        setState { state.value.copy(isResendLoading = false) }
    }

    internal fun setEmailVerificationCode(emailVerificationCode: String) {
        setState {
            state.value.copy(
                emailVerificationCode = emailVerificationCode,
            )
        }
        setButtonEnabled()
    }

    internal fun setTimerFinished(isFinished: Boolean) {
        setState { state.value.copy(isTimerFinished = isFinished)}
        if(isFinished) {
            setState {
                state.value.copy(
                    buttonEnabled = false,
                    textFieldError = SignUpTextFieldError.EmailVerificationCodeTimeExpired(),
                )
            }
        }
    }

    private fun setButtonEnabled() = setState {
        val buttonEnabled = state.value.emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH && !state.value.isTimerFinished
        state.value.copy(buttonEnabled = buttonEnabled)
    }

    internal fun onNextClick() = viewModelScope.launch {
        setState { state.value.copy(isLoading = true, buttonEnabled = false) }
        checkEmailVerificationCodeUseCase(
            email = route.signUpData.email,
            code = state.value.emailVerificationCode,
            type = EmailVerificationType.SIGNUP,
        ).onSuccess {
            postSideEffect(
                EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber(
                    signUpData = route.signUpData.copy(
                        authCode = state.value.emailVerificationCode,
                    ),
                ),
            )
        }.onFailure { exception ->
            setState { state.value.copy(textFieldError = SignUpTextFieldError.InvalidEmailVerificationCode()) }
            Logger.e(exception) { exception.message.toString() }
        }
        setState { state.value.copy(isLoading = false, buttonEnabled = true) }
    }

}

data class EnterEmailVerificationCodeState(
    val email: String = "",
    val emailVerificationCode: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isResendLoading: Boolean = false,
    val isTimerFinished: Boolean = false,
    val textFieldError: TextFieldError = TextFieldError.None(),
)

sealed interface EnterEmailVerificationCodeSideEffect {
    data class MoveToEnterStudentNumber(val signUpData: SignUpData) :
        EnterEmailVerificationCodeSideEffect

    data object ShowSendErrorSnackBar : EnterEmailVerificationCodeSideEffect
    data object ResetCountDownTimer : EnterEmailVerificationCodeSideEffect
}
