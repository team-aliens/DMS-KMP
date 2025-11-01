package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_choice
import dmskmp.core.design_system.generated.resources.img_model_student
import dmskmp.core.design_system.generated.resources.img_percent
import dmskmp.core.design_system.generated.resources.img_student_tag
import dmskmp.core.design_system.generated.resources.img_volunteer
import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.designsystem.card.DmsApplicationCard
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.core.util.toDateString

@Composable
internal fun VoteContent(
    modifier: Modifier = Modifier,
    votes: List<VoteModel>,
    onNavigateVote: (VoteModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        contentPadding = PaddingValues(
            horizontal = 10.dp,
            vertical = 16.dp,
        ),
    ) {
        items(votes) { vote ->
            val icon = when(vote.voteType) {
                VoteType.STUDENT_VOTE -> Res.drawable.img_student_tag
                VoteType.OPTION_VOTE -> Res.drawable.img_choice
                VoteType.APPROVAL_VOTE -> Res.drawable.img_percent
                VoteType.MODEL_STUDENT_VOTE -> Res.drawable.img_model_student
            }
            DmsApplicationCard(
                title = vote.topicName,
                appliedTitle = null,
                period = "${vote.startTime.toDateString()} ~ ${vote.endTime.toDateString()}",
                description = vote.description,
                iconRes = icon,
                onClick = { onNavigateVote(vote) },
            )
        }
    }
}
