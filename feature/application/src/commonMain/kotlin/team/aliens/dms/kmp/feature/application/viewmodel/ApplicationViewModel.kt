package team.aliens.dms.kmp.feature.application.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.core.model.votes.VoteModel


internal class ApplicationViewModel(
    private val getAllVotesUseCase: GetAllVotesUseCase,
) : BaseViewModel<ApplicationState,ApplicationSideEffect>(ApplicationState()){

    init {
        getAllVotes()
    }

    private fun getAllVotes() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllVotesUseCase()
                .onSuccess {
                    setState { state.value.copy(votes = it) }
                }.onFailure {
                    Logger.a(it) { it.message.toString() }
                }
        }
    }
}

data class ApplicationState(
    val votes: List<VoteModel> = emptyList(),
)

sealed interface ApplicationSideEffect
