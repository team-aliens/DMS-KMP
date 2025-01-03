package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class EnterStudentNumberViewModel :
    BaseViewModel<EnterStudentNumberState, EnterStudentNumberSideEffect>(EnterStudentNumberState.getDefaultState()) {

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
            val isStudentNumberNotBlank = grade.isNotBlank() && classroom.isNotBlank() && number.isNotBlank()
            copy(buttonEnabled = isStudentNumberNotBlank)
        }
    }

    internal fun onNextClick() {
        postSideEffect(
            EnterStudentNumberSideEffect.MoveToSetId(
                grade = "",
                classroom = "",
                number = "",
            )
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
        val grade: String,
        val classroom: String,
        val number: String,
    ) : EnterStudentNumberSideEffect
}
