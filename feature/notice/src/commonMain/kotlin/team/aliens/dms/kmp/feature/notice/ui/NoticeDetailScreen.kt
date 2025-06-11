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
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.notice.NoticeDetailModel
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticeDetailsState
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticeDetailsViewModel

@Composable
internal fun NoticeDetails(
    onNavigateBack: () -> Unit,
) {
    val viewModel: NoticeDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    NoticeDetailsScreen(
        state = state,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun NoticeDetailsScreen(
    state: NoticeDetailsState,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            title = "안내",
            onBackPressed = onNavigateBack,
        )
        Notice(
            notice = state.notice,
        )
    }
}

@Composable
private fun Notice(
    modifier: Modifier = Modifier,
    notice: NoticeDetailModel,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsText(
            text = notice.title,
            style = DmsTypography.Header3,
            color = DmsTheme.colors.surfaceBright,
        )
        DmsText(
            text = "${notice.createdAt.date} ${notice.createdAt.time}",
            style = DmsTypography.Body1,
            color = DmsTheme.colors.inverseSurface,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
        DmsText(
            text = notice.content,
            style = DmsTypography.Body2,
            color = DmsTheme.colors.onTertiaryContainer,
        )
    }
}
