package tema.aliens.dms.kmp.feature.remain.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.tag.DmsTag
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
internal fun RemainOptionCard(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    title: String,
    description: String,
    isApplied: Boolean,
    onClick: (Boolean) -> Unit,
) {
    var showDetail by remember { mutableStateOf(false) }
    val icon = if(showDetail) DmsIcon.Up else DmsIcon.Down
    val (borderColor,contentColor) = if (isSelected) DmsTheme.colors.inversePrimary to DmsTheme.colors.inversePrimary else DmsTheme.colors.surface to DmsTheme.colors.surfaceBright
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(!isSelected) },
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = DmsTheme.colors.background,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 14.dp,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            DmsText(
                text = title,
                style = DmsTypography.Title2,
                color = contentColor,
            )
            if (isApplied) {
                DmsTag(text = "신청 완료")
            }
            Spacer(modifier = Modifier.weight(1f))
            DmsIconButton(
                resource = icon,
                onClick = { showDetail = !showDetail },
                tint = contentColor,
            )
        }
        AnimatedVisibility(visible = showDetail) {
            DmsText(
                modifier = Modifier.topPadding(24.dp),
                text = description,
                style = DmsTypography.Body3,
                color = DmsTheme.colors.onBackground,
            )
        }
    }
}

