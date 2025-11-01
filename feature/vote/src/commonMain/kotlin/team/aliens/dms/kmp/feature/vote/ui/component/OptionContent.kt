package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.votes.VoteItemModel

@Composable
internal fun OptionContent(
    modifier: Modifier = Modifier,
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    options: List<VoteItemModel>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        TitleContent(
            title = title,
            startTime = startTime,
            endTime = endTime,
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 0.4.dp,
            color = DmsTheme.colors.onSurfaceVariant,
        )
        LazyColumn {
            items(options) { option ->
                OptionItem(
                    title = option.votingOptionName,
                    selected = option.id == selectItem,
                    onClick = { onSelect(option.id) },
                )
            }
        }
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (selected) {
        DmsTheme.colors.surfaceVariant
    } else {
        DmsTheme.colors.background
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 24.dp,
                vertical = 18.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsText(
            modifier = Modifier.startPadding(12.dp),
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
}
