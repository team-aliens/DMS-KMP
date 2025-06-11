package team.aliens.dms.kmp.feature.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
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

@Composable
internal fun AnnouncementCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
            ),
        shape = CircleShape,
        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.padding(start = 22.dp),
                painter = painterResource(DmsIcon.Notification),
                contentDescription = null,
            )
            DmsText(
                modifier = Modifier.padding(start = 28.dp),
                text = "새로운 공지가 있습니다.",
                style = DmsTypography.Body3,
                color = DmsTheme.colors.tertiaryContainer,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .background(
                        color = DmsTheme.colors.primary,
                        shape = CircleShape,
                    )
                    .padding(6.dp),
                painter = painterResource(DmsIcon.Forward),
                contentDescription = null,
                tint = DmsTheme.colors.inversePrimary,
            )
        }
    }
}
