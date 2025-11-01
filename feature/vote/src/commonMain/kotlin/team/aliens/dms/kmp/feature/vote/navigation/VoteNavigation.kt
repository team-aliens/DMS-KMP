package team.aliens.dms.kmp.feature.vote.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.common.navtype.VoteModelNavType
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.votes.VoteModel
import team.aliens.dms.kmp.feature.vote.ui.Vote

@Serializable
data class VoteRoute(
    val vote: VoteModel,
) {
    companion object {
        val NavTypeMap = mapOf(VoteModelNavType)
    }
}

fun NavController.navigateToVote(
    voteModel: VoteModel,
    navOptions: NavOptions? = null,
) = navigate(
    route = VoteRoute(
        vote = voteModel,
    ),
    navOptions = navOptions,
)

fun NavGraphBuilder.vote(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<VoteRoute>(
        typeMap = VoteRoute.NavTypeMap,
    ) {
        Vote(
            onShowSnackBar = onShowSnackBar,
            onNavigateBack = onNavigateBack,
        )
    }
}
