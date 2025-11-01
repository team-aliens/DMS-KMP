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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_calendar
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.button.DmsItemButton
import team.aliens.dms.kmp.core.designsystem.content.DmsPointContent
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.home.component.AnnouncementButton
import team.aliens.dms.kmp.feature.home.component.HomeTopAppBar
import team.aliens.dms.kmp.feature.home.component.MealContent
import team.aliens.dms.kmp.feature.home.viewmodel.HomeState
import team.aliens.dms.kmp.feature.home.viewmodel.HomeViewModel

@Composable
internal fun Home(
    onNavigateNotice: () -> Unit,
    onNavigateNoticeDetail: (String) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onNavigateNotice = onNavigateNotice,
        onNavigatePointHistory = onNavigatePointHistory,
        onNavigateMeal = onNavigateMeal,
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onNavigateNotice: () -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onNavigateMeal: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding(),
    ) {
        HomeTopAppBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(horizontal = 10.dp, vertical = 16.dp),
        ) {
            val brush = Brush.verticalGradient(
                listOf(
                    Color.White.copy(alpha = 0.03f),
                    Color(0xFF3D8AFF).copy(alpha = 0.15f),
                ),
            )
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.5f)
//                    .background(brush)
//                    .align(Alignment.BottomCenter),
//            )
            AnnouncementButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),
                //.dmsShadowModifier(DmsShadowType.Light20),
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
