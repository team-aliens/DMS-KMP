package team.aliens.dms.kmp.feature.vote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.vote.ui.component.VoteContent
import team.aliens.dms.kmp.feature.vote.viewmodel.VoteSideEffect
import team.aliens.dms.kmp.feature.vote.viewmodel.VoteState
import team.aliens.dms.kmp.feature.vote.viewmodel.VoteViewModel

@Composable
internal fun Vote(
    viewModel: VoteViewModel = koinViewModel(),
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when(it) {
                is VoteSideEffect.VoteSuccess -> onShowSnackBar(DmsSnackBarType.SUCCESS,"투표를 완료했어요!")
                is VoteSideEffect.VoteConflict -> onShowSnackBar(DmsSnackBarType.ERROR,"이미 해당 투표에 참여했어요")
                is VoteSideEffect.VoteFail -> onShowSnackBar(DmsSnackBarType.ERROR,"투표 중 오류가 발생했어요")
                is VoteSideEffect.VoteLoadFail -> onShowSnackBar(DmsSnackBarType.ERROR,"정보를 불러오지 못했어요")
            }
        }
    }

    VoteScreen(
        state = state,
        onNavigateBack = onNavigateBack,
        onSelectItem = viewModel::setSelectId,
        submitVote = viewModel::postVote,
    )
}

@Composable
private fun VoteScreen(
    modifier: Modifier = Modifier,
    state: VoteState,
    onNavigateBack: () -> Unit,
    onSelectItem: (String) -> Unit,
    submitVote: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        DmsTopAppBar(
            title = "투표",
            onBackPressed = onNavigateBack,
        )
        VoteContent(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            voteType = state.voteType,
            title = state.voteName,
            options = state.options,
            students = state.students,
            modelStudents = state.modelStudent,
            selectItem = state.selectId ?: "",
            onSelect = onSelectItem,
        )
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 80.dp,
                    bottom = 18.dp,
                ),
            text = "투표하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = submitVote,
            enabled = state.buttonEnabled,
        )
    }
}
