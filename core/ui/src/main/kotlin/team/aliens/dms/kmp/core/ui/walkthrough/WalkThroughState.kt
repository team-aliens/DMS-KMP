package team.aliens.dms.kmp.core.ui.walkthrough

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Stable
class WalkThroughState<T>(
    initialStep: Int = 0,
    private val onNavigateUp: () -> Unit = {},
) {
    private var stepProvider: () -> List<WalkThroughStepEntry<T>> by mutableStateOf({ emptyList() })
    private var _currentStep by mutableIntStateOf(initialStep)

    val currentStep: Int get() = _currentStep
    val isLastStep: Boolean get() = _currentStep == stepProvider().lastIndex
    val isFirstStep: Boolean get() = _currentStep == 0

    val currentType: T?
        get() = stepProvider().getOrNull(_currentStep)?.type

    fun next() {
        val steps = stepProvider()
        if (steps.isEmpty()) return
        _currentStep = (_currentStep + 1).coerceIn(0, steps.lastIndex)
    }

    fun previous() {
        if (_currentStep == 0) {
            onNavigateUp()
        } else {
            val steps = stepProvider()
            if (steps.isEmpty()) return
            _currentStep = (_currentStep - 1).coerceIn(0, steps.lastIndex)
        }
    }

    internal fun setStepProvider(provider: () -> List<WalkThroughStepEntry<T>>) {
        stepProvider = provider
    }

    fun reset() {
        _currentStep = 0
    }

    fun goTo(type: T) {
        val index = stepProvider().indexOfFirst { it.type == type }
        if (index != -1) {
            _currentStep = index
        }
    }
}

@Composable
fun <T> rememberWalkThroughState(
    initialStep: Int = 0,
    onNavigateUp: () -> Unit = {},
): WalkThroughState<T> = remember {
    WalkThroughState(
        initialStep = initialStep,
        onNavigateUp = onNavigateUp,
    )
}
