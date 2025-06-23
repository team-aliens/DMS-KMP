package team.aliens.dms.kmp.feature.application.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.tab.DmsTabRow
import team.aliens.dms.kmp.core.designsystem.text.DmsText
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
    onNavigateVote: (VoteModel) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    ApplicationScreen(
        state = state,
        onNavigateRemainApplication = onNavigateRemainApplication,
        onNavigateOutingApplication = onNavigateOutingApplication,
        onNavigateVote = onNavigateVote,
    )
}

@Composable
private fun ApplicationScreen(
    state: ApplicationState,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVote: (VoteModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
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
                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        DmsText(
                            text = text,
                            style = DmsTypography.Label,
                        )
                    },
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
                        onNavigateOutingApplication = onNavigateOutingApplication,
                        onNavigateRemainApplication = onNavigateRemainApplication,
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
