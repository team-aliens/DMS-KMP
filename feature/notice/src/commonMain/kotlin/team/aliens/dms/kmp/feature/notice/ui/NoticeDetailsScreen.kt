package team.aliens.dms.kmp.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticeDetailsState
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticeDetailsViewModel

@Composable
internal fun NoticeDetails(
    noticeId: Long,
) {
    val viewModel: NoticeDetailsViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    NoticeDetailsScreen(state = state)
}

@Composable
private fun NoticeDetailsScreen(
    state: NoticeDetailsState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            title = "안내",
            onBackPressed = { },
        )
        Notice()
    }

}

@Composable
private fun Notice(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsText(
            text = "기숙사생들에 알립니다.",
            style = DmsTypography.Header3,
            color = DmsTheme.colors.surfaceBright,
        )
        DmsText(
            text = "22/21/22 12:1",
            style = DmsTypography.Body1,
            color = DmsTheme.colors.inverseSurface,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
        DmsText(
            text = "sdasdasdasd",
            style = DmsTypography.Body2,
            color = DmsTheme.colors.onTertiaryContainer,
        )
    }
}
