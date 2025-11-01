package team.aliens.dms.kmp.feature.point.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.tab.DmsTab
import team.aliens.dms.kmp.core.designsystem.tab.DmsTabRow
import team.aliens.dms.kmp.feature.point.component.PointItem
import team.aliens.dms.kmp.feature.point.model.PointTab
import team.aliens.dms.kmp.feature.point.viewmodel.PointHistoryState
import team.aliens.dms.kmp.feature.point.viewmodel.PointHistoryViewModel

@Composable
internal fun PointHistoryScreen(
    onBackClick: () -> Unit,
    viewModel: PointHistoryViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    PointHistory(
        state = state,
        onBackClick = onBackClick,
    )
}

@Composable
private fun PointHistory(
    state: PointHistoryState,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackClick,
            title = "상벌점 내역 확인",
        )
        val tabData = listOf(
            PointTab.All,
            PointTab.Bonus,
            PointTab.Minus,
        )
        val pagerState = rememberPagerState(
            pageCount = { tabData.size },
            initialPage = state.initialTab,
        )
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, tab ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = tab.title,
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
                val pointHistoryList = remember(page, state.allPointList) {
                    when (tabData[page]) {
                        PointTab.All -> state.allPointList
                        PointTab.Bonus -> state.bonusPointList
                        PointTab.Minus -> state.minusPointList
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        items = pointHistoryList,
                        key = { it.id },
                    ) {
                        PointItem(
                            modifier = Modifier.fillMaxWidth(),
                            name = it.name,
                            point = it.score,
                            date = it.date,
                            pointType = it.type,
                        )
                    }
                }
            }
        }
    }
}
