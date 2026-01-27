package team.aliens.dms.kmp.feature.editpassword.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.user.ComparePasswordUseCase

class CheckPasswordViewModel(
    private val comparePasswordUseCase: ComparePasswordUseCase,
) : BaseViewModel<CheckPasswordState, CheckPasswordSideEffect>(CheckPasswordState()) {

    internal fun checkPassword() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            comparePasswordUseCase(
                password = state.value.currentPassword,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(CheckPasswordSideEffect.SuccessCheckPassword)
            }.onFailure {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(CheckPasswordSideEffect.FailCheckPassword("비밀번호가 일치하지 않아요"))
            }
        }
    }

    internal fun setPassword(password: String) {
        setState { state.value.copy(currentPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(state.value) {
            val isSignInValueNotBlank = currentPassword.isNotBlank()
            state.value.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }
}

data class CheckPasswordState(
    val currentPassword: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class CheckPasswordSideEffect {
    data object SuccessCheckPassword : CheckPasswordSideEffect()
    data class FailCheckPassword(val message: String) : CheckPasswordSideEffect()
}
