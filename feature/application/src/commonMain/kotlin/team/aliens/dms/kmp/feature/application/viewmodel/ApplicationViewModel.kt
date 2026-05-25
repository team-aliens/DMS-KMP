package team.aliens.dms.kmp.feature.application.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.core.model.votes.VoteModel

internal class ApplicationViewModel(
    private val getRemainUseCase: GetRemainUseCase,
    private val getAllVotesUseCase: GetAllVotesUseCase,
    private val lateStudyRepository: LateStudyRepository,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>(ApplicationState()) {

    init {
        getAllVotes()
        getRemain()
        getLateStudyStatus()
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

    internal fun getRemain() {
        viewModelScope.launch {
            getRemainUseCase()
                .onSuccess {
                    setState { state.value.copy(appliedTitle = it) }
                }.onFailure {
                    Logger.a(it) { it.message.toString() }
                }
        }
    }

    private fun getLateStudyStatus() {
        viewModelScope.launch {
            runCatching {
                lateStudyRepository.fetchMyStudyApplicationStatus()
            }.onSuccess {
                setState {
                    state.value.copy(
                        lateStudyAppliedTitle = it.status.toLateStudyAppliedTitle(),
                    )
                }
            }.onFailure {
                Logger.a(it) { it.message.toString() }
            }
        }
    }

    private fun String.toLateStudyAppliedTitle(): String? =
        when (this) {
            "PENDING" -> "신청 중"
            "SECOND_APPROVED" -> "승인됨"
            "REJECTED" -> "거절됨"
            else -> null
        }
}

data class ApplicationState(
    val appliedTitle: String? = null,
    val lateStudyAppliedTitle: String? = null,
    val votes: List<VoteModel> = emptyList(),
)

sealed interface ApplicationSideEffect
