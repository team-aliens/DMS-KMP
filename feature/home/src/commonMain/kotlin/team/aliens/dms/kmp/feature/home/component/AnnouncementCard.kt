package team.aliens.dms.kmp.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
internal fun AnnouncementButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(DmsTheme.colors.surfaceTint)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(DmsIcon.Notice),
            contentDescription = null,
            tint = DmsTheme.colors.scrim,
        )
        DmsText(
            modifier = Modifier.padding(start = 4.dp),
            text = "공지 보러가기",
            style = DmsTypography.labelB,
            color = DmsTheme.colors.tertiaryContainer,
        )
        DmsText(
            modifier = Modifier.padding(start = 8.dp),
            text = "기숙사 상벌점에 관하여",
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(DmsIcon.Forward),
            contentDescription = null,
            tint = DmsTheme.colors.scrim,
        )
    }
}
