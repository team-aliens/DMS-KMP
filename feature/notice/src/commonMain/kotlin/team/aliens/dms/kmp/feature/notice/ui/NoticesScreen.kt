package team.aliens.dms.kmp.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesState
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesViewModel

@Composable
internal fun Notices(
    onNoticeDetailsClick: (Long) -> Unit,
) {
    val viewModel: NoticesViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    NoticesScreen(
        state = state,
        onIsRecentChange = viewModel::setIsRecent,
        onNoticeDetailsClick = onNoticeDetailsClick,
    )
}

@Composable
private fun NoticesScreen(
    state: NoticesState,
    onIsRecentChange: () -> Unit,
    onNoticeDetailsClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(title = "안내")
        DmsButton(
            modifier = Modifier
                .startPadding(24.dp)
                .topPadding(20.dp),
            text = if (state.isRecent) "최신 순" else "오래된 순",
            buttonType = ButtonType.Outlined,
            buttonColor = ButtonColor.Primary,
            onClick = onIsRecentChange,
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(20.dp),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
        NoticeItems(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp),
            onNoticeDetailsClick = onNoticeDetailsClick,
        )
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    onNoticeDetailsClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(5) {
            NoticeItem(
                onNoticeDetailsClick = onNoticeDetailsClick,
            )
        }
    }
}

@Composable
fun NoticeItem(
    onNoticeDetailsClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = { onNoticeDetailsClick(1) },
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(16.dp)
                .verticalPadding(20.dp),
        ) {
            DmsText(
                text = "제목이 들어갑니다.",
                style = DmsTypography.Body1,
                color = DmsTheme.colors.onBackground,
            )
            DmsText(
                text = "2024/10/11",
                style = DmsTypography.Body3,
                color = DmsTheme.colors.inverseSurface,
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
    }
}
