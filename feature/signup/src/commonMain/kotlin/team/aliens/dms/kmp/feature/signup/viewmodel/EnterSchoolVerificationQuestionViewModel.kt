package team.aliens.dms.kmp.feature.signup.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationQuestionAnswerCheckUseCase
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolVerificationQuestionCheckUseCase
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.navigation.SignUp

internal class EnterSchoolVerificationQuestionViewModel(
    savedStateHandle: SavedStateHandle,
    private val getSchoolVerificationQuestionCheckUseCase: GetSchoolVerificationQuestionCheckUseCase,
    private val getSchoolVerificationQuestionAnswerCheckUseCase: GetSchoolVerificationQuestionAnswerCheckUseCase,
) :
    BaseViewModel<EnterSchoolVerificationQuestionState, EnterSchoolVerificationQuestionSideEffect>(
        EnterSchoolVerificationQuestionState(),
    ) {

    private val route = savedStateHandle.toRoute<SignUp.Route.EnterSchoolVerificationQuestion>(
        typeMap = SignUp.Route.NavTypeMap,
    )

    init {
        setSchoolVerificationQuestion()
    }

    private fun setSchoolVerificationQuestion() {
        viewModelScope.launch {
            getSchoolVerificationQuestionCheckUseCase(schoolId = route.signUpData.schoolId)
                .onSuccess { question ->
                    setState {
                        state.value.copy(
                            schoolVerificationQuestion = question,
                        )
                    }
                }.onFailure {
                    postSideEffect(EnterSchoolVerificationQuestionSideEffect.ShowQuestionErrorSnackBar)
                }
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
        viewModelScope.launch {
            setState { state.value.copy(isLoading = true) }
            getSchoolVerificationQuestionAnswerCheckUseCase(
                schoolId = route.signUpData.schoolId,
                answer = state.value.schoolVerificationAnswer,
            ).onSuccess {
                setState { state.value.copy(isLoading = false) }
                postSideEffect(
                    EnterSchoolVerificationQuestionSideEffect.MoveToEnterEmail(
                        signUpData = route.signUpData.copy(
                            schoolAnswer = state.value.schoolVerificationAnswer,
                        ),
                    ),
                )
            }.onFailure {
                setState { state.value.copy(isLoading = false) }
                postSideEffect(EnterSchoolVerificationQuestionSideEffect.ShowErrorSnackBar)
            }
        }
    }
}

data class EnterSchoolVerificationQuestionState(
    val schoolVerificationQuestion: String = "",
    val schoolVerificationAnswer: String = "",
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

sealed interface EnterSchoolVerificationQuestionSideEffect {
    data class MoveToEnterEmail(val signUpData: SignUpData) :
        EnterSchoolVerificationQuestionSideEffect

    data object ShowErrorSnackBar : EnterSchoolVerificationQuestionSideEffect
    data object ShowQuestionErrorSnackBar : EnterSchoolVerificationQuestionSideEffect
}
