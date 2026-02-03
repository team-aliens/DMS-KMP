package team.aliens.dms.kmp.feature.notification.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.notification.NotificationsModel

@Composable
internal fun NoticeItem(
    modifier: Modifier = Modifier,
    notice: NotificationsModel.NotificationModel,
    onNotificationDetailClick: (String, String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onNotificationDetailClick(notice.linkId, notice.id) })
            .padding(horizontal = 24.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(DmsIcon.Notice),
            tint = DmsTheme.colors.scrim,
            contentDescription = null,
        )
        Column(
            modifier = modifier.startPadding(12.dp),
        ) {
            DmsText(
                text = notice.title,
                style = DmsTypography.BodyM,
            )
            Row(
                modifier = modifier.topPadding(6.dp)
            ) {
                if (!notice.isRead) {
                    Icon(
                        modifier = modifier.size(4.dp),
                        imageVector = Icons.Filled.Circle,
                        contentDescription = null,
                        tint = DmsTheme.colors.primaryContainer,
                    )
                }
                DmsText(
                    modifier = modifier
                        .startPadding(4.dp),
                    text = notice.content,
                    style = DmsTypography.labelM,
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        DmsText(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = notice.elapsedText,
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
