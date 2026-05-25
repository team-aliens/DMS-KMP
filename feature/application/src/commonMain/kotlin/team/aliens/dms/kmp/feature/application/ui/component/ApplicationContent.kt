package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_home
import dmskmp.core.design_system.generated.resources.img_latestudy
import dmskmp.core.design_system.generated.resources.img_outing
import dmskmp.core.design_system.generated.resources.img_volunteer
import team.aliens.dms.kmp.core.designsystem.card.DmsApplicationCard

@Composable
internal fun ApplicationContent(
    modifier: Modifier = Modifier,
    appliedTitle: String?,
    onNavigateRemainApplication: () -> Unit,
    onNavigateOutingApplication: () -> Unit,
    onNavigateLateStudyApplication: () -> Unit,
    onNavigateVolunteerApplication: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = 10.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        DmsApplicationCard(
            title = "잔류",
            iconRes = Res.drawable.img_home,
            onClick = onNavigateRemainApplication,
            appliedTitle = appliedTitle,
        )

        DmsApplicationCard(
            title = "외출 신청하기",
            iconRes = Res.drawable.img_outing,
            onClick = onNavigateOutingApplication,
        )

        DmsApplicationCard(
            title = "새벽 자습 신청하기",
            iconRes = Res.drawable.img_latestudy,
            onClick = onNavigateLateStudyApplication,
        )

        DmsApplicationCard(
            title = "봉사 활동 신청하기",
            iconRes = Res.drawable.img_volunteer,
            onClick = onNavigateVolunteerApplication,
        )
    }
}
