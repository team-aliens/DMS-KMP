package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterSchoolVerificationQuestionViewModel(
    savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EnterSchoolVerificationQuestionState, EnterSchoolVerificationQuestionSideEffect>(
        EnterSchoolVerificationQuestionState.getDefaultState(),
    ) {

    private val route = savedStateHandle.toRoute<SignUpData>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    init {
        setSchoolVerificationQuestion()
    }

    private fun setSchoolVerificationQuestion() {
        val schoolVerificationQuestion = "우리 학교 학생 수를 입력해주세요"
        setState {
            state.value.copy(
                schoolVerificationQuestion = schoolVerificationQuestion,
            )
        }
    }

    internal fun setSchoolVerificationAnswer(schoolVerificationAnswer: String) {
        setState {
            state.value.copy(
                schoolVerificationAnswer = schoolVerificationAnswer,
            )
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = setState {
        val schoolVerificationAnswer = state.value.schoolVerificationAnswer
        state.value.copy(buttonEnabled = schoolVerificationAnswer.isNotEmpty())
    }

    internal fun onNextClick() {
        postSideEffect(
            EnterSchoolVerificationQuestionSideEffect.MoveToEnterEmail(
                signUpData = route.copy(
                    schoolAnswer = state.value.schoolVerificationAnswer,
                ),
            ),
        )
    }
}

data class EnterSchoolVerificationQuestionState(
    val schoolVerificationQuestion: String,
    val schoolVerificationAnswer: String,
    val buttonEnabled: Boolean,
) {
    companion object {
        fun getDefaultState() = EnterSchoolVerificationQuestionState(
            schoolVerificationQuestion = "",
            schoolVerificationAnswer = "",
            buttonEnabled = false,
        )
    }
}

sealed interface EnterSchoolVerificationQuestionSideEffect {
    data class MoveToEnterEmail(val signUpData: SignUpData) :
        EnterSchoolVerificationQuestionSideEffect
}
