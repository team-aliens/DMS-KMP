package team.aliens.dms.kmp.feature.signin.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.SignInUseCase

internal class SignInViewModel(
    private val signInUseCase: SignInUseCase,
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

    internal fun signIn() {
        viewModelScope.launch {
            val state = state.value
            signInUseCase.invoke(
                accountId = state.accountId,
                password = state.password,
                deviceToken = "",
            ).onSuccess {
                postSideEffect(SignInSideEffect.NavigateToMain)
            }.onFailure {
                println(it.printStackTrace())
            }
        }
    }
}

internal data class SignInState(
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

internal sealed interface SignInSideEffect {
    data object NavigateToMain: SignInSideEffect
}
