package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.domain.usecase.student.CheckEmailDuplicationUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterEmailViewModel(
    savedStateHandle: SavedStateHandle,
    private val checkEmailDuplicationUseCase: CheckEmailDuplicationUseCase,
) :
    BaseViewModel<EnterEmailState, EnterEmailSideEffect>(EnterEmailState()) {

    private val route = savedStateHandle.toRoute<SignUp.Route.EnterEmail>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    internal fun setEmail(email: String) {
        setState {
            state.value.copy(
                email = email,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val email = state.value.email
        state.value.copy(buttonEnabled = email.isNotEmpty())
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            checkEmailDuplicationUseCase(state.value.email)
                .onSuccess {
                    setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                    postSideEffect(
                        EnterEmailSideEffect.MoveToEnterEmailVerificationCode(
                            signUpData = route.signUpData.copy(
                                email = state.value.email,
                            ),
                        ),
                    )
                }.onFailure { exception ->
                    setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                    when (exception) {
                        is ConflictException -> postSideEffect(EnterEmailSideEffect.ShowConflictSnackBar)
                        else -> postSideEffect(EnterEmailSideEffect.ShowErrorSnackBar)
                    }
                }
        }
    }
}

data class EnterEmailState(
    val email: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface EnterEmailSideEffect {
    data class MoveToEnterEmailVerificationCode(val signUpData: SignUpData) : EnterEmailSideEffect
    data object ShowConflictSnackBar : EnterEmailSideEffect
    data object ShowErrorSnackBar : EnterEmailSideEffect
}
