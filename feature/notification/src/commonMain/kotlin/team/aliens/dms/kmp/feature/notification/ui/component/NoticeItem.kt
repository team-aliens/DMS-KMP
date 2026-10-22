package team.aliens.dms.kmp.feature.notification.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.notice.NoticeModel

@Composable
internal fun NoticeItem(
    modifier: Modifier = Modifier,
    notice: NoticeModel,
    onNotificationDetailClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNotificationDetailClick(notice.id) })
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
            style = DmsTypography.BodyM,
        )
        DmsText(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "${notice.createdAt.date}",
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
