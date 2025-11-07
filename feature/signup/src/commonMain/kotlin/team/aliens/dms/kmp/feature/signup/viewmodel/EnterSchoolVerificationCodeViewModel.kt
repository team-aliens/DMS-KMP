package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationCodeCheckUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.ui.SCHOOL_VERIFICATION_CODE_LENGTH

internal class EnterSchoolVerificationCodeViewModel(
    private val getSchoolVerificationCodeCheckUseCase: GetSchoolVerificationCodeCheckUseCase,
) :
    BaseViewModel<EnterSchoolVerificationCodeState, EnterSchoolVerificationCodeSideEffect>(
        EnterSchoolVerificationCodeState(),
    ) {

    internal fun setVerificationCode(verificationCode: String) {
        setState {
            state.value.copy(
                verificationCode = verificationCode,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val verificationCode = state.value.verificationCode
        state.value.copy(buttonEnabled = verificationCode.length == SCHOOL_VERIFICATION_CODE_LENGTH)
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            getSchoolVerificationCodeCheckUseCase(schoolCode = state.value.verificationCode)
                .onSuccess { schoolId ->
                    setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                    postSideEffect(
                        EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion(
                            signUpData = SignUpData().copy(
                                schoolId = schoolId,
                                schoolCode = state.value.verificationCode,
                            ),
                        ),
                    )
                }.onFailure {
                    setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                    postSideEffect(EnterSchoolVerificationCodeSideEffect.ShowErrorSnackBar)
                }
        }
    }
}

data class EnterSchoolVerificationCodeState(
    val email: String = "",
    val verificationCode: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface EnterSchoolVerificationCodeSideEffect {
    data class MoveToEnterSchoolVerificationQuestion(val signUpData: SignUpData) :
        EnterSchoolVerificationCodeSideEffect

    data object ShowErrorSnackBar : EnterSchoolVerificationCodeSideEffect
}
