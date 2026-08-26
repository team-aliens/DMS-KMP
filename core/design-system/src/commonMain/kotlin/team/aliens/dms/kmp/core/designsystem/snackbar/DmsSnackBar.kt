package team.aliens.dms.kmp.core.designsystem.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.ic_error
import dmskmp.core.design_system.generated.resources.ic_success
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.modifier.dmsDropShadow
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsSnackBar(
    modifier: Modifier = Modifier,
    snackBarType: DmsSnackBarType,
    message: String,
) {
    Row(
        modifier =
            modifier
                .wrapContentWidth()
                .dmsDropShadow(
                    shape = CircleShape,
                    color = DmsTheme.colors.onPrimaryContainer.copy(alpha = 0.1f),
                    blur = 20.dp,
                    offsetY = 0.dp,
                )
                .background(
                    color = DmsTheme.colors.surfaceTint,
                    shape = CircleShape,
                )
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp,
                ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Image(
            painter = painterResource(snackBarType.iconRes),
            contentDescription = null,
        )
        DmsText(
            text = message,
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.tertiaryContainer,
        )
    }
}

enum class DmsSnackBarType(
    val iconRes: DrawableResource,
) {
    SUCCESS(
        iconRes = Res.drawable.ic_success,
    ),
    ERROR(
        iconRes = Res.drawable.ic_error,
    ),
}
