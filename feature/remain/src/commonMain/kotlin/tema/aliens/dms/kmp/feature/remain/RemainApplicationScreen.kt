package tema.aliens.dms.kmp.feature.remain

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.common.ui.verticalPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.float.DmsFloatingNotice
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.tag.DmsTag
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
internal fun RemainApplication() {
    RemainApplicationScreen()
}

@Composable
private fun RemainApplicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            title = "잔류 신청",
            onBackPressed = {},
        )
        DmsFloatingNotice(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            text = "잔류 신청 시간은 화 18:00 ~ 목 18:00 까지 입니다.",
        )
        var isShowDetail by remember { mutableIntStateOf(0) }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(30.dp)
                .horizontalPadding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // 상세 보기는 한개만
            items(3) {
                var selected by remember { mutableStateOf(false) }
                RemainOptionCard(
                    isSelected = selected,
                    isShowDetail = true,
                    onClick = { selected = it },
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
            onClick = {},
        )
    }
}

@Composable
private fun RemainOptionCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    isShowDetail: Boolean,
    onClick: (Boolean) -> Unit,
) {
    var showDetail by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(!isSelected) },
            )
            .border(
                width = 1.dp,
                color = DmsTheme.colors.inversePrimary,
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = DmsTheme.colors.background,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 14.dp,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DmsText(
                text = "금요 귀가",
                style = DmsTypography.Title2,
                color = DmsTheme.colors.surfaceBright,
            )
            DmsTag(text = "신청 완료")
            Spacer(modifier = Modifier.weight(1f))
            DmsIconButton(
                resource = DmsIcon.Forward,
                onClick = { showDetail = !showDetail },
            )
        }
        AnimatedVisibility(visible = showDetail) {
            DmsText(
                modifier = Modifier.topPadding(24.dp),
                text = "금요일 일과가 모두 끝나고 8시 30분 이후부터9시 30분까지 귀가하고 일요일 6시 30분 부터 9시 30분까지 귀사해야 합니다. 혹시나 개인 일정으로 부득이하기 금요일이 아닌, 토요일 또는 일요일에 귀가해야 하는 학생들은 사감 선생님께 말씀부탁드립니다.",
                style = DmsTypography.Body3,
                color = DmsTheme.colors.onBackground,
            )
        }
    }
}
