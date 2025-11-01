package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.ic_approve
import dmskmp.core.design_system.generated.resources.ic_oppose
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.modifier.DmsShadowType
import team.aliens.dms.kmp.core.designsystem.modifier.dmsShadowModifier
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.votes.VoteItemModel

@Composable
internal fun ApprovalContent(
    modifier: Modifier = Modifier,
    title: String,
    options: List<VoteItemModel>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 60.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DmsText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = title,
            style = DmsTypography.TitleB,
            color = DmsTheme.colors.surfaceContainer,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            options.forEachIndexed { index, option ->
                if (index == 0) {
                    ApprovalItem(
                        modifier = Modifier.weight(1f),
                        imageResource = Res.drawable.ic_approve,
                        isSelected = option.id == selectItem,
                        clickColor = DmsTheme.colors.onPrimary,
                        clickBorderColor = DmsTheme.colors.onPrimaryContainer,
                        title = option.votingOptionName,
                        contentColor = DmsTheme.colors.onPrimaryContainer,
                        clickContentColor = DmsTheme.colors.inversePrimary,
                        onClick = { onSelect(option.id) },
                    )
                } else {
                    ApprovalItem(
                        modifier = Modifier.weight(1f),
                        imageResource = Res.drawable.ic_oppose,
                        isSelected = option.id == selectItem,
                        clickColor = DmsTheme.colors.onError,
                        clickBorderColor = DmsTheme.colors.onErrorContainer,
                        title = option.votingOptionName,
                        contentColor = DmsTheme.colors.onErrorContainer,
                        clickContentColor = DmsTheme.colors.outline,
                        onClick = { onSelect(option.id) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ApprovalItem(
    modifier: Modifier = Modifier,
    imageResource: DrawableResource,
    isSelected: Boolean,
    clickColor: Color,
    clickBorderColor: Color,
    contentColor: Color,
    clickContentColor: Color,
    title: String,
    onClick: () -> Unit,
) {
    val (backgroundColor, borderColor, content) = if (isSelected) {
        Triple(clickColor, clickBorderColor, clickContentColor)
    } else {
        Triple(DmsTheme.colors.surfaceTint, DmsTheme.colors.surfaceVariant, contentColor)
    }
    Card(
        modifier = modifier
            .dmsShadowModifier(
                dmsShadowType = DmsShadowType.Light20,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(
            width = 2.dp,
            color = borderColor,
        ),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = 20.dp,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(imageResource),
                tint = content,
                contentDescription = null,
            )
            DmsText(
                text = title,
                style = DmsTypography.BodyM,
                color = content,
            )
        }
    }
}
