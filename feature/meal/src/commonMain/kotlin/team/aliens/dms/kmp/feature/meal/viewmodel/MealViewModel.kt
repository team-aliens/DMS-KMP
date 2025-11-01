package team.aliens.dms.kmp.feature.meal.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.meal.GetMealUseCase
import team.aliens.dms.kmp.core.model.meal.MealModel
import team.aliens.dms.kmp.core.util.today

internal class MealViewModel(
    private val getMealUseCase: GetMealUseCase,
) : BaseViewModel<MealState, MealSideEffect>(MealState()) {

    init {
        getMeal()
    }

    private fun getMeal(date: LocalDate? = null) {
        val selectedDate = date ?: state.value.selectedDate
        viewModelScope.launch(Dispatchers.IO) {
            getMealUseCase(date = selectedDate)
                .onSuccess {
                    setState { state.value.copy(meal = it) }
                }
                .onFailure {
                    Logger.a(it) { it.message.toString() }
                }
        }
    }

    internal fun setDate(date: LocalDate) {
        setState { state.value.copy(selectedDate = date) }
        getMeal(date)
        hideCalendarBottomSheet()
    }

    internal fun setNextDate() {
        val date = state.value.selectedDate.plus(DatePeriod(days = 1))
        setState { state.value.copy(selectedDate = date) }
        getMeal(date)
    }

    internal fun setPreviousDate() {
        val date = state.value.selectedDate.minus(DatePeriod(days = 1))
        setState { state.value.copy(selectedDate = date) }
        getMeal(date)
    }

    internal fun showCalendarBottomSheet() {
        setState { state.value.copy(isShowCalendar = true) }
    }

    internal fun hideCalendarBottomSheet() {
        setState { state.value.copy(isShowCalendar = false) }
    }
}

internal data class MealState(
    val meal: MealModel = MealModel(),
    val selectedDate: LocalDate = today,
    val isShowCalendar: Boolean = false,
)

internal sealed interface MealSideEffect
