package team.aliens.dms.kmp.feature.application.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.tab.DmsTab
import team.aliens.dms.kmp.core.designsystem.tab.DmsTabRow
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.feature.application.ui.component.ApplicationContent
import team.aliens.dms.kmp.feature.application.ui.component.VoteContent
import team.aliens.dms.kmp.feature.application.viewmodel.ApplicationState
import team.aliens.dms.kmp.feature.application.viewmodel.ApplicationViewModel

@Composable
internal fun Application(
    viewModel: ApplicationViewModel = koinViewModel(),
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    ApplicationScreen(
        state = state,
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = { onShowSnackBar(DmsSnackBarType.SUCCESS, "준비중인 기능이에요") },
        onNavigateVolunteerApplication = { onShowSnackBar(DmsSnackBarType.SUCCESS, "준비중인 기능이에요") },
        onNavigateVote = onNavigateVote,
    )
}

@Composable
private fun ApplicationScreen(
    state: ApplicationState,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val tabData = listOf(
            "신청",
            "투표",
        )
        val pagerState = rememberPagerState(
            pageCount = { tabData.size },
            initialPage = 0,
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, text ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = text,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            beyondViewportPageCount = 1,
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (page == 0) {
                    ApplicationContent(
                        appliedTitle = state.appliedTitle,
                        onNavigateOutingApplication = onNavigateOutingApplication,
                        onNavigateRemainApplication = onNavigateRemainApplication,
                        onNavigateVolunteerApplication = onNavigateVolunteerApplication,
                    )
                } else {
                    VoteContent(
                        votes = state.votes,
                        onNavigateVote = onNavigateVote,
                    )
                }
            }
        }
    }
}
