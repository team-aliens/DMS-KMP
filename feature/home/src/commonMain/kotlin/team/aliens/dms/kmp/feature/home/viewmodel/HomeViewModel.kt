package team.aliens.dms.kmp.feature.home.viewmodel

import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.utils.today

internal class HomeViewModel :
    BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {

    internal fun updateDate(date: LocalDate) {
        setState { state.value.copy(selectedDate = date) }
    }
}

data class HomeState(
    val newNoticesExist: Boolean,
    val selectedDate: LocalDate,
) {
    companion object {
        fun getDefaultState() = HomeState(
            newNoticesExist = false,
            selectedDate = today,
        )
    }
}

sealed interface HomeSideEffect
