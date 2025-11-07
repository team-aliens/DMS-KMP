package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.domain.usecase.student.ExamineStudentNumberUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterStudentNumberViewModel(
    savedStateHandle: SavedStateHandle,
    private val examineStudentNumberUseCase: ExamineStudentNumberUseCase,
) :
    BaseViewModel<EnterStudentNumberState, EnterStudentNumberSideEffect>(EnterStudentNumberState()) {

    private val route = savedStateHandle.toRoute<SignUp.Route.EnterStudentNumber>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    internal fun setGrade(grade: String) {
        setState { state.value.copy(grade = grade) }
        buttonEnabled()
    }

    internal fun setClassRoom(classroom: String) {
        setState { state.value.copy(classroom = classroom) }
        buttonEnabled()
    }

    internal fun setNumber(number: String) {
        setState { state.value.copy(number = number) }
        buttonEnabled()
    }

    private fun buttonEnabled() = setState {
        with(state.value) {
            val isStudentNumberNotBlank =
                grade.isNotBlank() && classroom.isNotBlank() && number.isNotBlank()
            copy(buttonEnabled = isStudentNumberNotBlank)
        }
    }

    internal fun onNextClick() {
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            val grade = state.value.grade.toIntOrNull() ?: return@launch
            val classroom = state.value.classroom.toIntOrNull() ?: return@launch
            val number = state.value.number.toIntOrNull() ?: return@launch
            examineStudentNumberUseCase(
                schoolId = route.signUpData.schoolId,
                grade = grade,
                classroom = classroom,
                number = number,
            ).onSuccess {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                postSideEffect(
                    EnterStudentNumberSideEffect.MoveToSetId(
                        signUpData = route.signUpData.copy(
                            grade = grade,
                            classRoom = classroom,
                            number = number,
                        ),
                    ),
                )
            }.onFailure { exception ->
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                when (exception) {
                    is ConflictException -> postSideEffect(EnterStudentNumberSideEffect.ShowConflictSnackBar)
                    else -> postSideEffect(EnterStudentNumberSideEffect.ShowErrorSnackBar)
                }
            }
        }
    }
}

data class EnterStudentNumberState(
    val grade: String = "",
    val classroom: String = "",
    val number: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface EnterStudentNumberSideEffect {
    data class MoveToSetId(
        val signUpData: SignUpData,
    ) : EnterStudentNumberSideEffect

    data object ShowConflictSnackBar : EnterStudentNumberSideEffect
    data object ShowErrorSnackBar : EnterStudentNumberSideEffect
}
