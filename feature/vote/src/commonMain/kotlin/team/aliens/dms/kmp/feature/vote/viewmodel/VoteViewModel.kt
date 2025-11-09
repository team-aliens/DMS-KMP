package team.aliens.dms.kmp.feature.vote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.ConflictException
import team.aliens.dms.kmp.core.domain.usecase.student.GetCandidateModelStudentsUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.GetStudentsUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.GetVoteItemsUseCase
import team.aliens.dms.kmp.core.domain.usecase.votes.PostVoteUseCase
import team.aliens.dms.kmp.core.model.student.StudentModel
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteItemModel
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.core.util.today
import team.aliens.dms.kmp.feature.vote.navigation.VoteRoute

internal class VoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val getVoteItemsUseCase: GetVoteItemsUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
    private val getCandidateModelStudentsUseCase: GetCandidateModelStudentsUseCase,
    private val postVoteUseCase: PostVoteUseCase,
) : BaseViewModel<VoteState, VoteSideEffect>(VoteState()) {

    private val route = savedStateHandle.toRoute<VoteRoute>(typeMap = VoteRoute.NavTypeMap)

    init {
        initState()
        fetchVotesByType()
    }

    private fun initState() {
        setState {
            state.value.copy(
                vote = route.vote,
            )
        }
    }

    private fun fetchVotesByType() {
        when (route.vote.voteType) {
            VoteType.OPTION_VOTE -> getVoteItems()
            VoteType.STUDENT_VOTE -> getStudents()
            VoteType.APPROVAL_VOTE -> getVoteItems()
            VoteType.MODEL_STUDENT_VOTE -> getCandidateModelStudents()
        }
    }

    private fun getStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            getStudentsUseCase(name = null)
                .onSuccess { setState { state.value.copy(students = it) } }
                .onFailure {
                    Logger.a(it) { it.message.toString() }
                    postSideEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getVoteItems() {
        viewModelScope.launch(Dispatchers.IO) {
            getVoteItemsUseCase(votingTopicId = route.vote.id)
                .onSuccess { setState { state.value.copy(options = it) } }
                .onFailure {
                    Logger.a(it) { it.message.toString() }
                    postSideEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    private fun getCandidateModelStudents() {
        viewModelScope.launch(Dispatchers.IO) {
            getCandidateModelStudentsUseCase(requestDate = today)
                .onSuccess { setState { state.value.copy(modelStudent = it) } }
                .onFailure {
                    Logger.a(it) { it.message.toString() }
                    postSideEffect(VoteSideEffect.VoteLoadFail)
                }
        }
    }

    internal fun setSelectId(selectId: String) {
        setState { state.value.copy(selectId = selectId) }
        setButtonEnabled()
    }

    private fun setButtonEnabled() {
        val isSelectIdNotNull = !state.value.selectId.isNullOrBlank()
        setState { state.value.copy(buttonEnabled = isSelectIdNotNull) }
    }

    internal fun postVote() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { state.value.copy(isLoading = true, buttonEnabled = false) }
            postVoteUseCase(
                votingTopic = route.vote.id,
                selectId = state.value.selectId!!,
            ).onSuccess {
                setState { state.value.copy(buttonEnabled = false, isLoading = false) }
                postSideEffect(VoteSideEffect.VoteSuccess)
            }.onFailure {
                setState { state.value.copy(isLoading = false, buttonEnabled = true) }
                Logger.a(it) { it.message.toString() }
                when (it) {
                    is ConflictException -> postSideEffect(VoteSideEffect.VoteConflict)
                    else -> postSideEffect(VoteSideEffect.VoteFail)
                }
            }
        }
    }
}

internal data class VoteState(
    val vote: VoteModel = VoteModel(),
    val options: List<VoteItemModel> = emptyList(),
    val students: List<StudentModel> = emptyList(),
    val modelStudent: List<StudentModel> = emptyList(),
    val selectId: String? = null,
    val buttonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface VoteSideEffect {
    data object VoteSuccess : VoteSideEffect
    data object VoteConflict : VoteSideEffect
    data object VoteFail : VoteSideEffect
    data object VoteLoadFail : VoteSideEffect
}
