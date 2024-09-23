package team.aliens.dms.kmp.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param S 상속받는 뷰모델에서 사용될 state data class
 * @param E 상속받는 뷰모델에서 사용할 side effect sealed class
 */

abstract class BaseViewModel<S, E>(initialState: S) : ViewModel() {
    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _sideEffect: MutableSharedFlow<E> = MutableSharedFlow()
    val sideEffect = _sideEffect.asSharedFlow()

    protected fun setState(newState: () -> S) {
        _state.update {
            newState()
        }
    }

    protected fun postSideEffect(sideEffect: E) {
        viewModelScope.launch {
            _sideEffect.emit(sideEffect)
        }
    }
}
