package team.aliens.dms.kmp.core.designsystem.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
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
    // FIXME: https://youtrack.jetbrains.com/issue/CMP-6668
    /*IconButton(
        modifier = modifier.size(size),
        enabled = enabled,
        onClick = onClick,
    ) {
        Icon(
            painter = painterResource(resource),
            tint = tint,
            contentDescription = contentDescription,
        )
    }*/
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(resource),
            tint = tint,
            contentDescription = contentDescription,
        )
    }
}
