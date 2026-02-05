package team.aliens.dms.kmp.feature.notification.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.tab.DmsTab
import team.aliens.dms.kmp.core.designsystem.tab.DmsTabRow
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.notification.NotificationsModel
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.notification.ui.component.NoticeItem
import team.aliens.dms.kmp.feature.notification.viewmodel.NotificationSideEffect
import team.aliens.dms.kmp.feature.notification.viewmodel.NotificationState
import team.aliens.dms.kmp.feature.notification.viewmodel.NotificationViewModel

@Composable
internal fun Notification(
    onNavigateBack: () -> Unit,
    onNavigateNotificationDetailClick: (String) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: NotificationViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val tabData = listOf(
        "알림",
        "공지",
    )
    val pagerState = rememberPagerState(
        pageCount = { tabData.size },
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is NotificationSideEffect.FailFetchNotification -> onShowSnackBar(
                    DmsSnackBarType.ERROR, "알림 조회를 실패했어요"
                )
                is NotificationSideEffect.FailUpdateNotification -> onShowSnackBar(
                    DmsSnackBarType.ERROR, "업데이트 실패 했어요"
                )
            }
        }
    }

    NotificationScreen(
        state = state,
        tabData = tabData.toPersistentList(),
        pagerState = pagerState,
        tabIndex = tabIndex,
        onTabClick = { page ->
            coroutineScope.launch {
                pagerState.animateScrollToPage(page)
            }
        },
        onBackClick = onNavigateBack,
        onNotificationDetailClick = { linkId, notificationId ->
            viewModel.updateNotificationReadStatus(notificationId)
            onNavigateNotificationDetailClick(linkId)
        },
        onNotificationClick = { point, notificationId ->
            viewModel.updateNotificationReadStatus(notificationId)
            onNavigatePointHistory(point)
        },
    )
}

@Composable
private fun NotificationScreen(
    state: NotificationState,
    tabData: ImmutableList<String>,
    pagerState: PagerState,
    tabIndex: Int,
    onTabClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onNotificationDetailClick: (String, String) -> Unit,
    onNotificationClick: (PointType, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .systemBarsPadding(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackClick,
        )
        DmsTabRow(
            selectedTabIndex = tabIndex,
        ) {
            tabData.forEachIndexed { index, text ->
                DmsTab(
                    selected = tabIndex == index,
                    onClick = { onTabClick(index) },
                    text = text,
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            beyondViewportPageCount = 1
        ) { page ->
            if (page == 0) {
                NotificationItems(
                    notifications = state.notifications.toPersistentList(),
                    onNotificationClick = onNotificationClick,
                )
            } else {
                NoticeItems(
                    notices = state.notices,
                    onNotificationDetailClick = onNotificationDetailClick,
                )
            }
        }

    }
}

@Composable
internal fun NotificationItems(
    modifier: Modifier = Modifier,
    notifications: ImmutableList<NotificationsModel.NotificationModel>,
    onNotificationClick: (PointType, String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(
            items = notifications,
            key = { item -> item.id }
        ) {notification ->
            NotificationItem(
                notification = notification,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

@Composable
private fun NoticeItems(
    modifier: Modifier = Modifier,
    notices: List<NotificationsModel.NotificationModel>,
    onNotificationDetailClick: (String, String) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            items = notices,
            key = { item -> item.id },
        ) { notice ->
            NoticeItem(
                notice = notice,
                onNotificationDetailClick = onNotificationDetailClick,
            )
        }
    }
}

@Composable
internal fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: NotificationsModel.NotificationModel,
    onNotificationClick: (PointType, String) -> Unit,
) {
    val pointType = notification.pointDetailTopic ?: return
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNotificationClick(pointType, notification.id) })
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(if (pointType == PointType.MINUS) DmsIcon.Minus else DmsIcon.Plus),
            contentDescription = null,
        )
        Column(
            modifier = modifier.startPadding(12.dp),
        ) {
            Text(
                text = notification.title,
                style = DmsTypography.BodyM,
            )
            Row(
                modifier = modifier.topPadding(6.dp)
            ) {
                if (!notification.isRead) {
                    Icon(
                        modifier = modifier.size(4.dp),
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = DmsTheme.colors.primaryContainer,
                    )
                }
                Text(
                    modifier = modifier
                        .startPadding(4.dp),
                    text = notification.content,
                    style = DmsTypography.labelM,
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = notification.elapsedText,
            style = DmsTypography.BodyM,
            color = DmsTheme.colors.inverseSurface,
        )
        Icon(
            painter = painterResource(DmsIcon.Forward),
            tint = DmsTheme.colors.scrim,
            contentDescription = null,
        )
    }
}
