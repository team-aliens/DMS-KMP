package team.aliens.dms.kmp.core.designsystem.card

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable

@Composable
fun DmsApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    period: String? = null,
    appliedTitle: String? = null,
    iconRes: DrawableResource,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            DmsTheme.colors.onPrimaryContainer
        } else {
            DmsTheme.colors.surfaceTint
        },
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(DmsTheme.colors.surfaceTint)
            .clickable(onClick = onClick)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(32.dp),
            )
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(iconRes),
                contentDescription = null,
            )
            DmsText(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = DmsTypography.BodyB,
                color = DmsTheme.colors.inverseOnSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(DmsIcon.Forward),
                tint = DmsTheme.colors.scrim,
                contentDescription = null,
            )
        }
        period?.let {
            DmsText(
                text = period,
                style = DmsTypography.labelM,
                color = DmsTheme.colors.onPrimaryContainer,
            )
        }
        description?.let {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                DmsText(
                    text = description,
                    style = DmsTypography.labelM,
                    color = DmsTheme.colors.inverseSurface,
                )
                appliedTitle?.let {
                    AppliedTitleText(appliedTitle = appliedTitle)
                }
            }
        }
    }
}

@Composable
private fun AppliedTitleText(
    modifier: Modifier = Modifier,
    appliedTitle: String,
) {
    DmsText(
        modifier = modifier
            .background(
                color = DmsTheme.colors.primary,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 22.dp, vertical = 8.dp),
        text = appliedTitle,
        style = DmsTypography.labelB,
        color = DmsTheme.colors.onPrimaryContainer,
    )
}
