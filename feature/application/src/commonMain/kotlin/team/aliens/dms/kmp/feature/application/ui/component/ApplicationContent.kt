package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ApplicationContent(
    modifier: Modifier = Modifier,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 28.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        ApplicationCard(
            title = "잔류",
            description = "주말 기숙사 잔류 여부를 확인하고, 잔류 신청을 통해서 잔류 또는 귀가를 신청해 보세요.",
            buttonText = "잔류 신청하기",
            onButtonClick = onNavigateRemainApplication,
        )
        ApplicationCard(
            title = "외출",
            appliedTitle = "금요 귀가",
            description = "기숙사 생활 중 밖으로 나갈 일이 있다면, 외출 신청을 통해서 외출해 보세요.",
            buttonText = "외출 신청하기",
            onButtonClick = onNavigateOutingApplication,
        )
        ApplicationCard(
            title = "봉사 활동",
            description = "학생들이 직접 봉사 활동을 신청하고 신청한 활동에 참여할 수 있습니다.",
            buttonText = "봉사 신청하기",
            onButtonClick = onNavigateVolunteerApplication,
        )
    }
}
