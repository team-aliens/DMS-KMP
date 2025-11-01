package team.aliens.dms.kmp.feature.home.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
internal fun HomeTopAppBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.clickable(onClick = {}),
            painter = painterResource(DmsIcon.OutingPass),
            tint = DmsTheme.colors.scrim,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsIconButton(
            resource = DmsIcon.Notification,
            tint = DmsTheme.colors.scrim,
            contentPaddingValues = PaddingValues(2.dp),
            onClick = { },
        )
    }
}
