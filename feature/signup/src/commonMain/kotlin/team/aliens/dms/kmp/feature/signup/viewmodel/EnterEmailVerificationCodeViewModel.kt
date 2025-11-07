package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.core.model.type.EmailVerificationType
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

    private fun sendEmailVerificationCode() {
        viewModelScope.launch {
            sendEmailVerificationCodeUseCase(
                email = route.signUpData.email,
                type = EmailVerificationType.SIGNUP,
            ).onFailure {
                postSideEffect(EnterEmailVerificationCodeSideEffect.ShowSendErrorSnackBar)
            }
        }
    }

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
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            checkEmailVerificationCodeUseCase(
                email = route.signUpData.email,
                code = state.value.emailVerificationCode,
                type = EmailVerificationType.SIGNUP,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(
                    EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber(
                        signUpData = route.signUpData.copy(
                            authCode = state.value.emailVerificationCode,
                        ),
                    ),
                )
            }.onFailure {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(EnterEmailVerificationCodeSideEffect.ShowCheckErrorSnackBar)
            }
        }

    }
}

data class EnterEmailVerificationCodeState(
    val email: String = "",
    val emailVerificationCode: String = "",
    val timerFinished: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface EnterEmailVerificationCodeSideEffect {
    data class MoveToEnterStudentNumber(val signUpData: SignUpData) :
        EnterEmailVerificationCodeSideEffect

    data object ShowSendErrorSnackBar : EnterEmailVerificationCodeSideEffect
    data object ShowCheckErrorSnackBar : EnterEmailVerificationCodeSideEffect
}
