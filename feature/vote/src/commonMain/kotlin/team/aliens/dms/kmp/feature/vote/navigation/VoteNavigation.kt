package team.aliens.dms.kmp.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.common.navtype.VoteNavType
import team.aliens.dms.kmp.core.common.navtype.VoteTypeNavType
import team.aliens.dms.kmp.core.model.type.VoteType
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.feature.vote.ui.Vote

@Serializable
data class VoteRoute(
    val voteTopicId: String,
    val voteName: String,
    val voteType: VoteType,
) {
    companion object {
        val NavTypeMap = mapOf(VoteTypeNavType)
    }
}

fun NavController.navigateToVote(
    voteModel: VoteModel,
    navOptions: NavOptions? = null,
) = navigate(
    route = VoteRoute(
        voteTopicId = voteModel.id,
        voteName = voteModel.topicName,
        voteType = voteModel.voteType,
    ),
    navOptions = navOptions,
)

fun NavGraphBuilder.vote(
    onNavigateBack: () -> Unit,
) {
    composable<VoteRoute>(
        typeMap = VoteRoute.NavTypeMap,
    ) {
        Vote(onNavigateBack = onNavigateBack)
    }
}
