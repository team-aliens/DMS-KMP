package team.aliens.dms.kmp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_calendar
import kotlinx.coroutines.flow.collect
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.button.DmsItemButton
import team.aliens.dms.kmp.core.designsystem.content.DmsPointContent
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.home.component.AnnouncementButton
import team.aliens.dms.kmp.feature.home.component.HomeTopAppBar
import team.aliens.dms.kmp.feature.home.component.MealContent
import team.aliens.dms.kmp.feature.home.viewmodel.HomeSideEffect
import team.aliens.dms.kmp.feature.home.viewmodel.HomeState
import team.aliens.dms.kmp.feature.home.viewmodel.HomeViewModel

@Composable
internal fun Home(
    onNavigateNotice: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowOutingPassDialog -> onShowSnackBar(DmsSnackBarType.SUCCESS,"개발중인 기능이에요")
                is HomeSideEffect.NavigateToNotification -> onShowSnackBar(DmsSnackBarType.SUCCESS,"개발중인 기능이에요")
            }
        }
    }

    HomeScreen(
        state = state,
        onNavigateNotice = onNavigateNotice,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateMeal = onNavigateMeal,
        onOutingPassClick = viewModel::showOutingPassDialog,
        onNotificationClick = viewModel::navigateToNotification,
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onNavigateNotice: () -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
    onOutingPassClick: () -> Unit,
    onNotificationClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding(),
    ) {
        HomeTopAppBar(
            onOutingPassClick = onOutingPassClick,
            onNotificationClick = onNotificationClick,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(horizontal = 10.dp, vertical = 16.dp),
        ) {
            AnnouncementButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                onClick = onNavigateNotice,
            )
            MealContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onMealClick = onNavigateMeal,
            )
            DmsPointContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                plusPoint = state.myPage.bonusPoint,
                minusPoint = state.myPage.minusPoint,
                onClick = { },
            )
            DmsItemButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                iconRes = Res.drawable.img_calendar,
                text = "상벌점 이력 보러가기",
                onClick = { onNavigatePointHistory(PointType.ALL) },
            )
        }
    }
}
