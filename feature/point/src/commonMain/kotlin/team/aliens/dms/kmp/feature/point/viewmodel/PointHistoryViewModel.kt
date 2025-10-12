package team.aliens.dms.kmp.feature.point.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.points.GetPointsUseCase
import team.aliens.dms.kmp.core.model.points.PointModel
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.point.navigation.PointHistoryRoute

internal class PointHistoryViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPointsUseCase: GetPointsUseCase,
) : BaseViewModel<PointHistoryState, PointHistorySideEffect>(PointHistoryState()) {

    private val route = savedStateHandle.toRoute<PointHistoryRoute>()

    init {
        getPoints()
        initTab()
    }

    private fun getPoints() {
        viewModelScope.launch {
            getPointsUseCase(
                type = PointType.ALL,
                page = null,
                size = null,
            ).onSuccess { pointHistory ->
                val pointHistories = pointHistory.pointHistories
                val bonusPoints = pointHistories.filter { it.type == PointType.BONUS }
                val minusPoints = pointHistories.filter { it.type == PointType.MINUS }
                setState {
                    state.value.copy(
                        allPointList = pointHistories,
                        bonusPointList = bonusPoints,
                        minusPointList = minusPoints,
                    )
                }
            }
        }
    }

    private fun initTab() {
        val initialTab = when (route.pointType) {
            PointType.ALL -> 0
            PointType.BONUS -> 1
            PointType.MINUS -> 2
        }
        setState { state.value.copy(initialTab = initialTab) }
    }
}

internal data class PointHistoryState(
    val allPointList: List<PointModel> = emptyList(),
    val bonusPointList: List<PointModel> = emptyList(),
    val minusPointList: List<PointModel> = emptyList(),
    val initialTab: Int = 0,
)

internal sealed interface PointHistorySideEffect
