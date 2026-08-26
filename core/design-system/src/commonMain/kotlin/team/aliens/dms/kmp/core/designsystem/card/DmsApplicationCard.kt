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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.common.ui.endPadding
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable

enum class ApplicationBadgeStatus {
    APPROVED,
    REJECTED,
    PENDING,
}

@Composable
fun DmsApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    period: String? = null,
    appliedTitle: String? = null,
    appliedBadgeStatus: ApplicationBadgeStatus? = null,
    iconRes: DrawableResource,
    isSelected: Boolean = false,
    onClick: () -> Unit,
) {
    val borderColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                DmsTheme.colors.onPrimaryContainer
            } else {
                DmsTheme.colors.surfaceTint
            },
    )
    Column(
        modifier =
            modifier
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
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = title,
                style = DmsTypography.BodyB,
                color = DmsTheme.colors.inverseOnSurface,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (description == null && !appliedTitle.isNullOrEmpty()) {
                AppliedTitleText(
                    modifier = Modifier.endPadding(16.dp),
                    appliedTitle = appliedTitle,
                    badgeStatus = appliedBadgeStatus,
                )
            }
            Icon(
                painter = painterResource(DmsIcon.Forward),
                tint = DmsTheme.colors.scrim,
                contentDescription = null,
            )
        }
        period?.let {
            Text(
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
                Text(
                    text = description,
                    style = DmsTypography.labelM,
                    color = DmsTheme.colors.inverseSurface,
                )
                Spacer(modifier = Modifier.weight(1f))
                if (!appliedTitle.isNullOrEmpty()) {
                    AppliedTitleText(
                        appliedTitle = appliedTitle,
                        badgeStatus = appliedBadgeStatus,
                    )
                }
            }
        }
    }
}

@Composable
private fun AppliedTitleText(
    modifier: Modifier = Modifier,
    appliedTitle: String,
    badgeStatus: ApplicationBadgeStatus? = null,
) {
    val backgroundColor =
        when (badgeStatus) {
            ApplicationBadgeStatus.APPROVED,
            null,
            -> DmsTheme.colors.primary

            ApplicationBadgeStatus.REJECTED -> DmsTheme.colors.error
            ApplicationBadgeStatus.PENDING -> DmsTheme.colors.surfaceVariant
        }
    val textColor =
        when (badgeStatus) {
            ApplicationBadgeStatus.APPROVED,
            null,
            -> DmsTheme.colors.onPrimaryContainer

            ApplicationBadgeStatus.REJECTED -> DmsTheme.colors.onErrorContainer
            ApplicationBadgeStatus.PENDING -> DmsTheme.colors.inverseSurface
        }

    DmsText(
        modifier =
            modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(6.dp),
                )
                .padding(horizontal = 22.dp, vertical = 8.dp),
        text = appliedTitle,
        style = DmsTypography.labelB,
        color = textColor,
    )
}
