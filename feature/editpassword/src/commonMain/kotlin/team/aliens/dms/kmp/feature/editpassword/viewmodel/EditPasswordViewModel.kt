package team.aliens.dms.kmp.feature.editpassword.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel

class EditPasswordViewModel(
    private val userRepository: UserRepository,
): BaseViewModel<EditPasswordState, EditPasswordSideEffect>(EditPasswordState()) {

    internal fun setNewPassword(password: String) {
        setState { it.copy(newPassword = password) }
        setButtonEnabled()
    }

    internal fun setCheckNewPassword(password: String) {
        setState { it.copy(checkNewPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState { // TODO :: textfield description message 처리 고려 (타입에 따른 메세지 분기처리, 한 프로퍼티로 처리 등등)
        with(uiState.value) {
            val isSignInValueNotBlank = newPassword.isNotBlank() && checkNewPassword.isNotBlank() && newPassword == checkNewPassword
            it.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }

    internal fun editPassword(currentPassword: String) = run {
        if(!checkIfPasswordValid(uiState.value.checkNewPassword)) {
            sendEffect(EditPasswordSideEffect.PasswordMismatch("비밀번호가 형식에 맞지 않습니다"))
            return@run
        }
        if (currentPassword == uiState.value.checkNewPassword) {
            sendEffect(EditPasswordSideEffect.PasswordMismatch("기존 비밀번호는 변경 불가합니다"))
            return@run
        }
        viewModelScope.launch {
            setState { it.copy(isLoading = true, buttonEnabled = false) }
            userRepository.editPassword(
                currentPassword = currentPassword,
                newPassword = uiState.value.checkNewPassword,
            ).onSuccess {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(EditPasswordSideEffect.SuccessEditPassword)
            }.onFailure {
                setState { it.copy(isLoading = false, buttonEnabled = true) }
                sendEffect(EditPasswordSideEffect.FailEditPassword("비밀번호 변경에 실패했습니다"))
            }
        }
    }
}

data class EditPasswordState(
    val newPassword: String = "",
    val checkNewPassword: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed class EditPasswordSideEffect { // TODO :: 더 좋은 선택지 고려
    data object SuccessEditPassword : EditPasswordSideEffect()
    data class FailEditPassword(val message: String) : EditPasswordSideEffect()
    data class PasswordMismatch(val message: String) : EditPasswordSideEffect()
}
