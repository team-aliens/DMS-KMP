package team.aliens.dms.kmp.feature.signin.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.BadRequestException
import team.aliens.dms.kmp.core.common.exception.network.InternalServerErrorException
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.UnprocessableEntityException
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.domain.usecase.auth.SignInUseCase

internal class SignInViewModel(
    private val signInUseCase: SignInUseCase,
) : BaseViewModel<SignInState, SignInSideEffect>(SignInState()) {

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
            setState { state.copy(isLoading = true, buttonEnabled = false) }
            signInUseCase.invoke(
                accountId = state.accountId,
                password = state.password,
                deviceToken = "",
            ).onSuccess {
                setState { state.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(SignInSideEffect.NavigateToMain)
            }.onFailure {
                setState { state.copy(isLoading = false, buttonEnabled = true) }
                val errorMessage = when (it) {
                    is BadRequestException -> "잘못된 형식이에요"
                    is UnprocessableEntityException -> "비밀번호가 일치하지 않아요"
                    is NotFoundException -> "로그인 정보를 다시 확인해주세요"
                    is InternalServerErrorException -> "서버에 문제가 발생했어요"
                    else -> "일시적인 오류로 로그인을 할 수 없어요"
                }
                postSideEffect(SignInSideEffect.ShowSnackBar(DmsSnackBarType.ERROR, errorMessage))
            }
        }
    }
}

internal data class SignInState(
    val accountId: String = "",
    val password: String = "",
    val buttonEnabled: Boolean = false,
    val showAccountIdErrorDescription: Boolean = false,
    val showPasswordErrorDescription: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface SignInSideEffect {
    data object NavigateToMain : SignInSideEffect
    data class ShowSnackBar(
        val snackBarType: DmsSnackBarType,
        val message: String,
    ) : SignInSideEffect
}
