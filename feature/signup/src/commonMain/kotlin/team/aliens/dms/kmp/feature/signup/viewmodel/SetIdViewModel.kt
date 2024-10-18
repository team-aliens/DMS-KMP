package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel

internal class SetIdViewModel :
    BaseViewModel<SetIdState, SetIdSideEffect>(SetIdState.getDefaultState()) {

    internal fun onNextClick() {
        postSideEffect(
            SetIdSideEffect.MoveToSetPassword(
                id = "",
                grade = "",
                classroom = "",
                number = "",
            ),
        )
    }
}

data class SetIdState(
    val id: String,
    val grade: String,
    val classroom: String,
    val number: String,
) {
    companion object {
        fun getDefaultState() = SetIdState(
            id = "",
            grade = "",
            classroom = "",
            number = "",
        )
    }
}

sealed interface SetIdSideEffect {
    data class MoveToSetPassword(
        val id: String,
        val grade: String,
        val classroom: String,
        val number: String,
    ) : SetIdSideEffect
}