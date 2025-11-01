package tema.aliens.dms.kmp.feature.remain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_bus
import dmskmp.core.design_system.generated.resources.img_home
import dmskmp.core.design_system.generated.resources.img_night_bus
import dmskmp.core.design_system.generated.resources.img_small_home
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
import team.aliens.dms.kmp.core.common.util.toKorean
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.card.DmsApplicationCard
import team.aliens.dms.kmp.core.designsystem.float.DmsFloatingNotice
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
internal fun RemainApplication(
    onNavigateBack: () -> Unit,
) {
    val viewModel: RemainApplicationViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    RemainApplicationScreen(
        onNavigateBack = onNavigateBack,
        state = state,
        setSelectRemainsOption = viewModel::setSelectRemainsOption,
        changeRemainsOption = viewModel::changeRemainsOption,
    )
}

@Composable
private fun RemainApplicationScreen(
    onNavigateBack: () -> Unit,
    state: RemainApplicationState,
    setSelectRemainsOption: (String) -> Unit,
    changeRemainsOption: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "잔류 신청",
            onBackPressed = onNavigateBack,
        )
        DmsFloatingNotice(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            text = "잔류 신청 시간은 ${state.remainsApplicationTime.startDayOfWeek.toKorean()} ${state.remainsApplicationTime.startTime} ~ ${state.remainsApplicationTime.endDayOfWeek.toKorean()} ${state.remainsApplicationTime.endTime} 까지 입니다.",
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(30.dp)
                .horizontalPadding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(state.remainsOptions) { remainsOption ->
                val icon = when (remainsOption.title) {
                    "금요일 귀가" -> Res.drawable.img_night_bus
                    "토요일 귀가" -> Res.drawable.img_bus
                    "토요일 귀사" -> Res.drawable.img_home
                    "주말 잔류" -> Res.drawable.img_small_home
                    else -> Res.drawable.img_bus
                }
                val appliedTitle = if (remainsOption.isApplied) "신청됨" else null
                DmsApplicationCard(
                    title = remainsOption.title,
                    description = remainsOption.description,
                    isSelected = state.selectRemainsOptionId == remainsOption.id,
                    iconRes = icon,
                    appliedTitle = appliedTitle,
                    onClick = { setSelectRemainsOption(remainsOption.id) },
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .verticalPadding(16.dp),
            text = "변경하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled = state.selectRemainsOptionId.isNotEmpty(),
            onClick = changeRemainsOption,
        )
    }
}
