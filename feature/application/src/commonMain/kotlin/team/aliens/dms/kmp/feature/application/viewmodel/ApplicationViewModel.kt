package team.aliens.dms.kmp.feature.application.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.designsystem.card.ApplicationBadgeStatus
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel
import team.aliens.dms.kmp.core.model.votes.VoteModel

internal class ApplicationViewModel(
    private val getRemainUseCase: GetRemainUseCase,
    private val getAllVotesUseCase: GetAllVotesUseCase,
    private val lateStudyRepository: LateStudyRepository,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>(ApplicationState()) {

    init {
        getAllVotes()
        getRemain()
        refreshLateStudyStatus()
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

    fun refreshLateStudyStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                lateStudyRepository.fetchMyStudyApplicationStatus()
            }.onSuccess { status ->
                setState {
                    state.value.copy(
                        lateStudyApplicationStatus = status.toApplicationStatus(),
                    )
                }
            }.onFailure {
                Logger.a(it) { it.message.toString() }
            }
        }
    }
}

data class ApplicationState(
    val appliedTitle: String? = null,
    val lateStudyApplicationStatus: LateStudyApplicationStatus? = null,
    val votes: List<VoteModel> = emptyList(),
)

sealed interface ApplicationSideEffect

data class LateStudyApplicationStatus(
    val title: String,
    val badgeStatus: ApplicationBadgeStatus,
)

private fun StudyApplicationStatusModel.toApplicationStatus(): LateStudyApplicationStatus? = when (status) {
    "APPROVED", "SECOND_APPROVED" -> LateStudyApplicationStatus(
        title = buildRangeText("승인됨") ?: "승인됨",
        badgeStatus = ApplicationBadgeStatus.APPROVED,
    )

    "PENDING" -> LateStudyApplicationStatus(
        title = "신청 중",
        badgeStatus = ApplicationBadgeStatus.PENDING,
    )

    "REJECTED" -> LateStudyApplicationStatus(
        title = buildRangeText("거절됨") ?: "거절됨",
        badgeStatus = ApplicationBadgeStatus.REJECTED,
    )

    else -> null
}

private fun StudyApplicationStatusModel.buildRangeText(
    suffix: String,
): String? {
    val startDate = startDate
    val endDate = endDate

    return when {
        !startDate.isNullOrBlank() && !endDate.isNullOrBlank() -> {
            val formattedStartDate = startDate.toMonthDayOrNull() ?: return null
            val formattedEndDate = endDate.toMonthDayOrNull() ?: return null

            if (startDate == endDate) "$formattedStartDate $suffix"
            else "$formattedStartDate ~ $formattedEndDate $suffix"
        }

        !startDate.isNullOrBlank() -> startDate.toMonthDayOrNull()?.let { "$it $suffix" }
        !endDate.isNullOrBlank() -> endDate.toMonthDayOrNull()?.let { "$it $suffix" }
        else -> null
    }
}

private fun String.toMonthDayOrNull(): String? = runCatching {
    LocalDate.parse(this).let { "${it.monthNumber}.${it.dayOfMonth}" }
}.getOrNull()
