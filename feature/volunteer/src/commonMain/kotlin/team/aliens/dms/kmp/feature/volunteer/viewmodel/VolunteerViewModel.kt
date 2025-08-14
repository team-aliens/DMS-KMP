package team.aliens.dms.kmp.feature.volunteer.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.auth.GetTokenUseCase

internal class VolunteerViewModel(
    private val getTokenUseCase: GetTokenUseCase,
) : BaseViewModel<VolunteerState, VolunteerSideEffect>(VolunteerState()) {

    init {
        getToken()
    }

    private fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            getTokenUseCase().onSuccess { token ->
                token?.let {
                    setState { state.value.copy(jwtToken = it.accessToken.value) }
                }
            }
        }
    }
}

internal data class VolunteerState(
    val jwtToken: String = "",
)

internal sealed interface VolunteerSideEffect
