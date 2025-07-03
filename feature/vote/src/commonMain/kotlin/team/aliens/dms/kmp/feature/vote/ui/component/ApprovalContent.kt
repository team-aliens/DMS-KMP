package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    ) {
        DmsText(
            modifier = Modifier.padding(top = 20.dp, start = 24.dp, end = 24.dp),
            text = title,
            style = DmsTypography.Header3,
        )
        LazyRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            itemsIndexed(options) { index, option ->
                val (imageResource, clickColor) = if (index == 0) {
                    Res.drawable.ic_approve to DmsTheme.colors.onPrimary
                } else {
                    Res.drawable.ic_oppose to DmsTheme.colors.onError
                }
                ApprovalItem(
                    imageResource = imageResource,
                    isSelected = option.id == selectItem,
                    clickColor = clickColor,
                    onClick = { onSelect(option.id) },
                )
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
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        clickColor
    } else {
        DmsTheme.colors.background
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
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 1.dp,
            color = DmsTheme.colors.onSurface,
        ),
    ) {
        Image(
            modifier = Modifier
                .padding(
                    vertical = 60.dp,
                    horizontal = 42.dp,
                )
                .size(100.dp),
            painter = painterResource(imageResource),
            contentDescription = null,
        )
    }
}
