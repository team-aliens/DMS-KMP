package team.aliens.dms.kmp.feature.editpassword.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.util.Regex
import team.aliens.dms.kmp.core.domain.usecase.user.EditPasswordUseCase
import team.aliens.dms.kmp.feature.editpassword.navigation.EditPasswordRoute

class EditPasswordViewModel(
    savedStateHandle: SavedStateHandle,
    private val editPasswordUseCase: EditPasswordUseCase,
) : BaseViewModel<EditPasswordState, EditPasswordSideEffect>(EditPasswordState()) {

    private val route = savedStateHandle.toRoute<EditPasswordRoute>()

    internal fun setNewPassword(password: String) {
        setState { state.value.copy(newPassword = password) }
        setButtonEnabled()
    }

    internal fun setCheckNewPassword(password: String) {
        setState { state.value.copy(checkNewPassword = password) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState { // TODO :: textfield description message 처리 고려 (타입에 따른 메세지 분기처리, 한 프로퍼티로 처리 등등)
        with(state.value) {
            val isSignInValueNotBlank = newPassword.isNotBlank() && checkNewPassword.isNotBlank() && newPassword == checkNewPassword
            state.value.copy(buttonEnabled = isSignInValueNotBlank)
        }
    }

    internal fun editPassword() = run {
        if (!Regex(Regex.PASSWORD).matches(state.value.checkNewPassword)) {
            postSideEffect(EditPasswordSideEffect.PasswordMismatch("비밀번호가 형식에 맞지 않습니다"))
            return@run
        }
        if (route.currentPassword == state.value.checkNewPassword) {
            postSideEffect(EditPasswordSideEffect.PasswordMismatch("기존 비밀번호는 변경 불가합니다"))
            return@run
        }
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            editPasswordUseCase(
                password = route.currentPassword,
                newPassword = state.value.checkNewPassword,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(EditPasswordSideEffect.SuccessEditPassword)
            }.onFailure {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(EditPasswordSideEffect.FailEditPassword("비밀번호 변경에 실패했습니다"))
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
