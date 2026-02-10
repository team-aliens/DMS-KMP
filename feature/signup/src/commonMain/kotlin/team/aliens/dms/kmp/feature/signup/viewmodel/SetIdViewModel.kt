package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.domain.usecase.student.CheckIdDuplicationUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class SetIdViewModel(
    savedStateHandle: SavedStateHandle,
    private val checkIdDuplicationUseCase: CheckIdDuplicationUseCase
) :
    BaseViewModel<SetIdState, SetIdSideEffect>(SetIdState()) {

    private val route = savedStateHandle.toRoute<SignUp.Route.SetId>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    internal fun setId(id: String) {
        setState { state.value.copy(id = id) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val id = state.value.id
        state.value.copy(buttonEnabled = id.isNotEmpty())
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            checkIdDuplicationUseCase(
                id = state.value.id,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(
                    SetIdSideEffect.MoveToSetPassword(
                        signUpData = route.signUpData.copy(accountId = state.value.id),
                    ),
                )
            }.onFailure { exception ->
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                when (exception) {
                    is ConflictException -> postSideEffect(SetIdSideEffect.ShowConflictSnackBar)
                    else -> postSideEffect(SetIdSideEffect.ShowErrorSnackBar)
                }
            }
        }
    }
}

internal data class SetIdState(
    val id: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface SetIdSideEffect {
    data class MoveToSetPassword(
        val signUpData: SignUpData,
    ) : SetIdSideEffect

    data object ShowConflictSnackBar : SetIdSideEffect
    data object ShowErrorSnackBar : SetIdSideEffect
}
