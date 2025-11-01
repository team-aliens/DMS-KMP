package team.aliens.dms.kmp.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
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
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
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
            .horizontalPadding(10.dp)
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(24.dp),
    ) {
        DmsText(
            text = notice.title,
            style = DmsTypography.LBodyB,
            color = DmsTheme.colors.tertiaryContainer,
        )
        DmsText(
            modifier = Modifier.topPadding(8.dp),
            text = "${notice.createdAt.date} ${notice.createdAt.time}",
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseOnSurface,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .verticalPadding(18.dp),
            thickness = 1.dp,
            color = DmsTheme.colors.scrim,
        )
        DmsText(
            text = notice.content,
            style = DmsTypography.BodyM,
            color = DmsTheme.colors.tertiaryContainer,
        )
    }
}
