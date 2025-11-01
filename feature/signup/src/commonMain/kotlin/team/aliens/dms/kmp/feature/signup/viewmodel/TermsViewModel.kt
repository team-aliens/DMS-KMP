package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.student.SignUpUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class TermsViewModel(
    savedStateHandle: SavedStateHandle,
    private val signUpUseCase: SignUpUseCase,
) :
    BaseViewModel<TermsState, TermsSideEffect>(TermsState()) {

    private val route = savedStateHandle.toRoute<SignUpData>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    internal fun postSignUp() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            signUpUseCase(
                schoolVerificationCode = route.schoolCode,
                schoolVerificationAnswer = route.schoolAnswer,
                email = route.email,
                emailVerificationCode = route.authCode,
                grade = route.grade,
                classRoom = route.classRoom,
                number = route.number,
                accountId = route.accountId,
                password = route.password,
                profileImageUrl = route.profileImageUrl,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(TermsSideEffect.NavigateToComplete)
            }.onFailure { exception ->
                postSideEffect(TermsSideEffect.FailSignUp)
                Logger.a(exception) { exception.message.toString() }
            }
        }
    }

    internal fun setButtonEnabled(buttonEnabled: Boolean) {
        setState { state.value.copy(buttonEnabled = buttonEnabled) }
    }
}

data class TermsState(
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface TermsSideEffect {
    data object NavigateToComplete : TermsSideEffect
    data object FailSignUp : TermsSideEffect
}
