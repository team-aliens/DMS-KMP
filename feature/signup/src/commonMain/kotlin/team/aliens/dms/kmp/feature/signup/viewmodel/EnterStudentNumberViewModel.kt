package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterStudentNumberViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EnterStudentNumberState, EnterStudentNumberSideEffect>(EnterStudentNumberState.getDefaultState()) {

    private val route = savedStateHandle.toRoute<SignUpData>(
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
        postSideEffect(
            EnterStudentNumberSideEffect.MoveToSetId(
                signUpData = route.copy(
                    grade = state.value.grade.toInt(),
                    classRoom = state.value.classroom.toInt(),
                    number = state.value.number.toInt(),
                ),
            ),
        )
    }
}

data class EnterStudentNumberState(
    val grade: String,
    val classroom: String,
    val number: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterStudentNumberState(
            grade = "",
            classroom = "",
            number = "",
            buttonEnabled = false,
        )
    }
}

sealed interface EnterStudentNumberSideEffect {
    data class MoveToSetId(
        val signUpData: SignUpData,
    ) : EnterStudentNumberSideEffect
}
