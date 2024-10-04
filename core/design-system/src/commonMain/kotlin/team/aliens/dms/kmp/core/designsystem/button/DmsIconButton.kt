package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun DmsIconButton(
    modifier: Modifier = Modifier,
    resource: DrawableResource,
    tint: Color,
    enabled: Boolean = true,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
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
