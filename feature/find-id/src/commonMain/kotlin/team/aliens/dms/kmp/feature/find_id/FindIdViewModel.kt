package team.aliens.dms.kmp.feature.find_id

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.UnAuthorizedException
import team.aliens.dms.kmp.core.domain.usecase.schools.GetSchoolsUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.FindIdUseCase
import team.aliens.dms.kmp.core.model.student.EmailModel
import team.aliens.dms.kmp.core.util.guardAll

internal class FindIdViewModel(
    private val getSchoolsUseCase: GetSchoolsUseCase,
    private val findIdUseCase: FindIdUseCase,
) : BaseViewModel<FindIdState, FindIdSideEffect>(FindIdState()) {

    internal fun setName(name: String) = viewModelScope.launch {
        setState {
           state.value.copy(name = name)
        }
        setButtonEnabled()
    }

    internal fun setGrade(grade: String) = viewModelScope.launch {
        setState {
            state.value.copy(grade = grade)
        }
        setButtonEnabled()
    }

    internal fun setClassRoom(classRoom: String) = viewModelScope.launch {
        setState {
            state.value.copy(classRoom = classRoom)
        }
        setButtonEnabled()
    }

    internal fun setNumber(number: String) = viewModelScope.launch {
        setState {
            state.value.copy(number = number)
        }
        setButtonEnabled()
    }

    private fun setButtonEnabled() = viewModelScope.launch {
        setState {
            with(state.value) {
                copy(buttonEnabled = name.isNotEmpty() && grade.isNotEmpty() && classRoom.isNotEmpty() && number.isNotEmpty())
            }
        }
    }

    internal fun findId() = viewModelScope.launch {
        val schoolId = getSchoolsUseCase().getOrNull()?.firstOrNull()?.id ?: run {
            postSideEffect(FindIdSideEffect.ShowServerErrorSnackBar)
            return@launch
        }
        val (grade, classroom, number) = guardAll(
            state.value.grade.toIntOrNull(),
            state.value.classRoom.toIntOrNull(),
            state.value.number.toIntOrNull(),
        ) ?: run {
            postSideEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
            return@launch
        }

        findIdUseCase(
            schoolId = schoolId,
            studentName = state.value.name,
            grade = grade,
            classRoom = classroom,
            number = number,
        ).onSuccess { email ->
            setState { state.value.copy(email = email, isShowIdDialog = true) }
        }.onFailure { exception ->
            when(exception) {
                is UnAuthorizedException -> postSideEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
                is NotFoundException -> postSideEffect(FindIdSideEffect.ShowNumberErrorSnackBar)
                else -> postSideEffect(FindIdSideEffect.ShowServerErrorSnackBar)
            }
        }
    }

    internal fun navigateBack() = viewModelScope.launch {
        postSideEffect(FindIdSideEffect.NavigateBack)
    }

    internal fun hideDialog() = viewModelScope.launch {
        setState { state.value.copy(isShowIdDialog = false) }
    }
}

internal data class FindIdState(
    val name: String = "",
    val grade: String = "",
    val classRoom: String = "",
    val number: String = "",
    val email: EmailModel = EmailModel(),
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isShowIdDialog: Boolean = false,
)

internal sealed interface FindIdSideEffect {
    data object NavigateBack : FindIdSideEffect
    data object ShowNumberErrorSnackBar : FindIdSideEffect
    data object ShowServerErrorSnackBar : FindIdSideEffect
}
