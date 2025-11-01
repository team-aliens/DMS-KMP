package team.aliens.dms.kmp.feature.notice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.notice.NoticeModel
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesState
import team.aliens.dms.kmp.feature.notice.viewmodel.NoticesViewModel

@Composable
internal fun Notices(
    onNavigateBack: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
) {
    val viewModel: NoticesViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    NoticesScreen(
        state = state,
        onBackClick = onNavigateBack,
        onIsRecentChange = viewModel::setIsRecent,
        onNoticeDetailClick = onNoticeDetailClick,
    )
}

@Composable
private fun NoticesScreen(
    state: NoticesState,
    onBackClick: () -> Unit,
    onIsRecentChange: () -> Unit,
    onNoticeDetailClick: (String) -> Unit,
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
            onBackPressed = onBackClick,
        )
//        DmsButton(
//            modifier = Modifier
//                .startPadding(24.dp)
//                .topPadding(20.dp),
//            text = if (state.isRecent) "최신 순" else "오래된 순",
//            buttonType = ButtonType.Outlined,
//            buttonColor = ButtonColor.Primary,
//            onClick = onIsRecentChange,
//        )
        NoticeItems(
            modifier = Modifier
                .fillMaxWidth(),
            notices = state.notices,
            onNoticeDetailClick = onNoticeDetailClick,
        )
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    notices: List<NoticeModel>,
    onNoticeDetailClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(
            items = notices,
            key = { item -> item.id },
        ) { notice ->
            NoticeItem(
                notice = notice,
                onNoticeDetailClick = onNoticeDetailClick,
            )
        }
    }
}

@Composable
fun NoticeItem(
    modifier: Modifier = Modifier,
    notice: NoticeModel,
    onNoticeDetailClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNoticeDetailClick(notice.id) })
            .padding(horizontal = 24.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(DmsIcon.Notice),
            tint = DmsTheme.colors.scrim,
            contentDescription = null,
        )
        DmsText(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
            text = notice.title,
            maxLines = 1,
            style = DmsTypography.Body1,
            color = DmsTheme.colors.onBackground,
        )
        DmsText(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "${notice.createdAt.date}",
            style = DmsTypography.Body3,
            color = DmsTheme.colors.inverseSurface,
        )
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colors.scrim,
            contentDescription = null,
        )
    }
}
