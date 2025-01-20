package team.aliens.dms.kmp.core.designsystem.float

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsFloatingNotice(
    modifier: Modifier = Modifier,
    text: String,
    iconResource: DrawableResource = DmsIcon.Notification,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DmsTheme.colors.primary,
                shape = RoundedCornerShape(30.dp),
            ).padding(
                horizontal = 22.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
        )
        DmsText(
            text = text,
            style = DmsTypography.Body3,
            color = DmsTheme.colors.onBackground,
        )
    }
}
