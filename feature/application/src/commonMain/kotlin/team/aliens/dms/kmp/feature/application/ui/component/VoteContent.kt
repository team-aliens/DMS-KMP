package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.model.votes.VoteModel

@Composable
internal fun VoteContent(
    modifier: Modifier = Modifier,
    votes: List<VoteModel>,
    onNavigateVote: (VoteModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(
            horizontal = 24.dp,
            vertical = 16.dp,
        ),
    ) {
        items(votes) { vote ->
            VoteItem(
                startTime = vote.startTime,
                endTime = vote.endTime,
                title = vote.topicName,
                description = vote.description,
                onClick = { onNavigateVote(vote) },
            )
        }
    }
}
