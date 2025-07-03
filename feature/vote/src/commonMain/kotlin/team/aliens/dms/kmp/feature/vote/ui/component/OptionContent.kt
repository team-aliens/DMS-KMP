package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.votes.VoteItemModel

@Composable
internal fun OptionContent(
    modifier: Modifier = Modifier,
    title: String,
    options: List<VoteItemModel>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        DmsText(
            modifier = Modifier.padding(start = 24.dp, top = 20.dp, bottom = 8.dp),
            text = title,
            style = DmsTypography.Header3,
        )
        LazyColumn {
            items(options) { option ->
                OptionItem(
                    name = option.votingOptionName,
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
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val background = if (selected) {
        DmsTheme.colors.primary
    } else {
        DmsTheme.colors.background
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(background)
            .clickable(onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 14.dp),
    ) {
        DmsText(
            text = name,
            style = DmsTypography.Body1,
        )
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = DmsTheme.colors.surface,
    )
}
