package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class EnterSchoolVerificationCodeViewModel :
    BaseViewModel<EnterSchoolVerificationCodeState, EnterSchoolVerificationCodeSideEffect>(
        EnterSchoolVerificationCodeState.getDefaultState(),
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
        state.value.copy(buttonEnabled = verificationCode.isNotEmpty())
    }

    internal fun onNextClick() {
        postSideEffect(
            EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion(
                schoolCode = "",
            ),
        )
    }
}

data class EnterSchoolVerificationCodeState(
    val verificationCode: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterSchoolVerificationCodeState(
            verificationCode = "",
            buttonEnabled = false,
        )
    }
}

sealed interface EnterSchoolVerificationCodeSideEffect {
    data class MoveToEnterSchoolVerificationQuestion(val schoolCode: String) :
        EnterSchoolVerificationCodeSideEffect
}
