package team.aliens.dms.kmp.feature.signup.viewmodel

import team.aliens.dms.kmp.core.common.base.BaseViewModel


internal class SetPasswordViewModel :
    BaseViewModel<SetPasswordState, SetPasswordSideEffect>(SetPasswordState.getDefaultState()) {

    internal fun onNextClick() {
        postSideEffect(
            SetPasswordSideEffect.MoveToTerms(
                password = ""
            ),
        )
    }
}

data class SetPasswordState(
    val password: String,
) {
    companion object {
        fun getDefaultState() = SetPasswordState(
            password = ""
        )
    }
}

sealed interface SetPasswordSideEffect {
    data class MoveToTerms(
        val password: String,
    ) : SetPasswordSideEffect
}
