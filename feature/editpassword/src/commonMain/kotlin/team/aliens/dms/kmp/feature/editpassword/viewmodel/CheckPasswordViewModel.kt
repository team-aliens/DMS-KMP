package team.aliens.dms.kmp.feature.editpassword.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel


class CheckPasswordViewModel(
    val userRepository: UserRepository
): BaseViewModel<CheckPasswordState, CheckPasswordSideEffect>(CheckPasswordState()) {

    internal fun resetPassword() {
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            userRepository.comparePassword(
                password = uiState.value.currentPassword,
            ).onSuccess {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(CheckPasswordSideEffect.SuccessCheckPassword)
            }.onFailure {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(CheckPasswordSideEffect.FailCheckPassword("비밀번호가 일치하지 않아요"))
            }
        }
    }

    internal fun setPassword(password: String) {
        setState { it.copy(currentPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        with(uiState.value) {
            val isSignInValueNotBlank = currentPassword.isNotBlank()
            it.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }
}

data class CheckPasswordState(
    val currentPassword: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class CheckPasswordSideEffect() {
    data object SuccessCheckPassword : CheckPasswordSideEffect()
    data class FailCheckPassword(val message: String) : CheckPasswordSideEffect()
}
