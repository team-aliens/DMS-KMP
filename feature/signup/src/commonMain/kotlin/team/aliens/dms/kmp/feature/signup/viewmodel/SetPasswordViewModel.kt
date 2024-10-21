package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.utils.Regex

internal class SetPasswordViewModel :
    BaseViewModel<SetPasswordState, SetPasswordSideEffect>(SetPasswordState.getDefaultState()) {

    internal fun setPassword(password: String) {
        setState {
            state.value.copy(
                password = password,
                showPasswordDescription = !Regex(Regex.PASSWORD)
                    .matches(password),
            )
        }
        setButtonEnabled()
    }

    internal fun setPasswordCheck(passwordCheck: String) {
        setState {
            with(state.value) {
                copy(
                    passwordCheck = passwordCheck,
                    showCheckPasswordDescription = password != passwordCheck,
                )
            }
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(state.value) {
            val hasBlank = password.isBlank() || passwordCheck.isBlank()
            val hasInvalidation = showPasswordDescription || showCheckPasswordDescription
            copy(buttonEnabled = !hasBlank && !hasInvalidation)
        }
    }

    internal fun onNextClick() {
        postSideEffect(
            SetPasswordSideEffect.MoveToTerms(
                password = "",
            ),
        )
    }
}

data class SetPasswordState(
    val password: String,
    val passwordCheck: String,
    val showPasswordDescription: Boolean,
    val showCheckPasswordDescription: Boolean,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = SetPasswordState(
            password = "",
            passwordCheck = "",
            showPasswordDescription = false,
            showCheckPasswordDescription = false,
            buttonEnabled = false,
        )
    }
}

sealed interface SetPasswordSideEffect {
    data class MoveToTerms(
        val password: String,
    ) : SetPasswordSideEffect
}
