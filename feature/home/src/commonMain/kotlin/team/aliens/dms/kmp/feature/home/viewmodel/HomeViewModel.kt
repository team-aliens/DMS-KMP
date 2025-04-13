package team.aliens.dms.kmp.feature.home.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.utils.today
import team.aliens.dms.kmp.core.domain.usecase.meal.GetMealUseCase
import team.aliens.dms.kmp.core.model.meal.MealModel

internal class HomeViewModel(
    private val getMealUseCase: GetMealUseCase,
) : BaseViewModel<HomeState, HomeSideEffect>(HomeState.getDefaultState()) {

    init {
        getMeal()
    }

    internal fun updateDate(date: LocalDate) {
        setState { state.value.copy(selectedDate = date) }
    }

    private fun getMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            getMealUseCase(date = today)
                .onSuccess {
                    setState { state.value.copy(meal = it) }
                }
                .onFailure {
                    Logger.a(it) { it.message.toString() }
                }
        }
    }
}

data class HomeState(
    val newNoticesExist: Boolean,
    val selectedDate: LocalDate,
    val meal: MealModel,
) {
    companion object {
        fun getDefaultState() = HomeState(
            newNoticesExist = false,
            selectedDate = today,
            meal = MealModel(
                date = today,
                breakfast = emptyList(),
                kcalBreakfast = null,
                lunch = emptyList(),
                kcalLunch = null,
                dinner = emptyList(),
                kcalDinner = null,
            ),
        )
    }
}

sealed interface HomeSideEffect
