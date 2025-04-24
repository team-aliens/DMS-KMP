package team.aliens.dms.kmp.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.koin.compose.viewmodel.koinViewModel
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
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesState
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesViewModel

@Composable
internal fun Notices(
    onNoticeDetailsClick: (String) -> Unit,
) {
    val viewModel: NoticesViewModel = koinViewModel()
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
    onNoticeDetailsClick: (String) -> Unit,
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
            notices = state.notices,
            onNoticeDetailsClick = onNoticeDetailsClick,
        )
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    notices: List<NoticeModel>,
    onNoticeDetailsClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = notices,
            key = { item -> item.id },
        ) { notice ->
            NoticeItem(
                title = notice.title,
                date = notice.createdAt,
                onNoticeDetailsClick = onNoticeDetailsClick,
            )
        }
    }
}

@Composable
fun NoticeItem(
    title: String,
    date: LocalDateTime,
    onNoticeDetailsClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable(
                onClick = { onNoticeDetailsClick("") },
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(16.dp)
                .verticalPadding(20.dp),
        ) {
            DmsText(
                text = title,
                style = DmsTypography.Body1,
                color = DmsTheme.colors.onBackground,
            )
            DmsText(
                text = "${date.date} ${date.time}",
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
