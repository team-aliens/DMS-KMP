package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DmsIconButton(
    modifier: Modifier = Modifier,
    resource: DrawableResource,
    tint: Color,
    enabled: Boolean = true,
    size: Dp = 24.dp,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier.size(size),
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(resource),
            tint = tint,
            contentDescription = contentDescription,
        )
    }
}
