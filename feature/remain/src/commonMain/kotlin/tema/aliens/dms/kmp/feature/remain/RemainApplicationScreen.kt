package tema.aliens.dms.kmp.feature.remain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
import team.aliens.dms.kmp.core.common.utils.toKorean
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.float.DmsFloatingNotice
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import tema.aliens.dms.kmp.feature.remain.component.RemainOptionCard

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
            .background(DmsTheme.colors.background),
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.remainsOptions) { remainsOption ->
                RemainOptionCard(
                    isSelected = state.selectRemainsOptionId == remainsOption.id,
                    title = remainsOption.title,
                    description = remainsOption.description,
                    isApplied = remainsOption.isApplied,
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
