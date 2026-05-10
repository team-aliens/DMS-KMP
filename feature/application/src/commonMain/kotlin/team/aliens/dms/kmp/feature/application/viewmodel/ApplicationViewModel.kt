package team.aliens.dms.kmp.feature.application.viewmodel

import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.data.latestudy.repository.LateStudyRepository
import team.aliens.dms.kmp.core.domain.usecase.remains.GetRemainUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetAllVotesUseCase
import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel
import team.aliens.dms.kmp.core.model.votes.VoteModel

@OptIn(ExperimentalTime::class)
internal class ApplicationViewModel(
    private val getRemainUseCase: GetRemainUseCase,
    private val getAllVotesUseCase: GetAllVotesUseCase,
    private val lateStudyRepository: LateStudyRepository,
) : BaseViewModel<ApplicationState, ApplicationSideEffect>(ApplicationState()) {

    init {
        getAllVotes()
        getRemain()
        fetchMyStudyApplicationStatus()
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

    private fun fetchMyStudyApplicationStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                lateStudyRepository.fetchMyStudyApplicationStatus()
            }.onSuccess { studyApplicationStatus ->
                val title = studyApplicationStatus.toAppliedTitle()
                val statusUi = studyApplicationStatus.toUiStatus()

                setState {
                    state.value.copy(
                        lateStudyAppliedTitle = title ?: state.value.lateStudyAppliedTitle,
                        lateStudyStatusUi = statusUi ?: state.value.lateStudyStatusUi,
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
    val lateStudyAppliedTitle: String? = null,
    val lateStudyStatusUi: LateStudyStatusUi? = null,
    val votes: List<VoteModel> = emptyList(),
)

enum class LateStudyStatusUi {
    SECOND_APPROVED,
    REJECTED,
    PENDING,
}

sealed interface ApplicationSideEffect

@OptIn(ExperimentalTime::class)
private fun StudyApplicationStatusModel.toAppliedTitle(): String? {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    val start = startDate.toLocalDateOrNull()
    val end = endDate.toLocalDateOrNull()

    return when (status) {
        "SECOND_APPROVED" -> approvedTitle(today, start, end)
        "PENDING" -> "신청 중"
        "REJECTED" -> rejectedTitle(today, start, end)
        else -> null
    }
}

private fun StudyApplicationStatusModel.approvedTitle(
    today: LocalDate,
    start: LocalDate?,
    end: LocalDate?,
): String? {
    val actualEndDate = end ?: start
    if (actualEndDate == null || today > actualEndDate) return null
    return buildRangeText(start, end, "승인됨")
}

private fun StudyApplicationStatusModel.rejectedTitle(
    today: LocalDate,
    start: LocalDate?,
    end: LocalDate?,
): String? {
    val rejectBaseDate = end ?: start
    if (rejectBaseDate == null || rejectBaseDate != today) return null
    return buildRangeText(start, end, "거절됨") ?: "거절됨"
}

private fun StudyApplicationStatusModel.buildRangeText(
    start: LocalDate?,
    end: LocalDate?,
    suffix: String,
): String? {
    return when {
        start != null && end != null -> {
            if (start == end) "$startDate $suffix"
            else "$startDate ~ $endDate $suffix"
        }
        start != null -> "$startDate $suffix"
        end != null -> "$endDate $suffix"
        else -> null
    }
}

@OptIn(ExperimentalTime::class)
private fun StudyApplicationStatusModel.toUiStatus(): LateStudyStatusUi? {
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    val start = startDate.toLocalDateOrNull()
    val end = endDate.toLocalDateOrNull()

    return when (status) {
        "SECOND_APPROVED" -> {
            val actualEndDate = end ?: start
            if (actualEndDate != null && today <= actualEndDate) {
                LateStudyStatusUi.SECOND_APPROVED
            } else {
                null
            }
        }
        "PENDING" -> LateStudyStatusUi.PENDING
        "REJECTED" -> {
            val rejectBaseDate = end ?: start
            if (rejectBaseDate != null && rejectBaseDate == today) {
                LateStudyStatusUi.REJECTED
            } else {
                null
            }
        }
        else -> null
    }
}

private fun String?.toLocalDateOrNull(): LocalDate? {
    if (this.isNullOrBlank()) return null
    return runCatching { LocalDate.parse(this) }.getOrNull()
}
