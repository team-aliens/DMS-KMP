package team.aliens.dms.kmp.feature.signin.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class SignInViewModel(

) : BaseViewModel<SignInState, SignInSideEffect>(SignInState.getDefaultState()) {

    internal fun setAccountId(accountId: String) {
        setState {
            state.value.copy(
                accountId = accountId,
                showAccountIdErrorDescription = false,
            )
        }
        setButtonEnabled()
    }

    internal fun setPassword(password: String) {
        setState {
            state.value.copy(
                password = password,
                showPasswordErrorDescription = false,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(state.value) {
            val isSignInValueNotBlank = accountId.isNotBlank() && password.isNotBlank()
            val hasNoError = !showAccountIdErrorDescription && !showPasswordErrorDescription
            copy(buttonEnabled = isSignInValueNotBlank && hasNoError)
        }
    }
}

data class SignInState(
    val accountId: String,
    val password: String,
    val buttonEnabled: Boolean,
    val showAccountIdErrorDescription: Boolean,
    val showPasswordErrorDescription: Boolean,
) {
    companion object {
        fun getDefaultState() = SignInState(
            accountId = "",
            password = "",
            buttonEnabled = false,
            showAccountIdErrorDescription = false,
            showPasswordErrorDescription = false,
        )
    }
}

sealed interface SignInSideEffect {
}
