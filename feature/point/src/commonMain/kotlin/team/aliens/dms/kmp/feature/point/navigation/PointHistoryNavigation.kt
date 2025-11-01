package team.aliens.dms.kmp.feature.point.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.point.ui.PointHistoryScreen

@Serializable
data class PointHistoryRoute(
    val pointType: String,
)

fun NavController.navigateToPointHistory(
    pointType: PointType,
    navOptions: NavOptions? = null,
) = navigate(
    route = PointHistoryRoute(
        pointType = pointType.name,
    ),
    navOptions = navOptions,
)

fun NavGraphBuilder.pointHistory(
    onNavigateBack: () -> Unit,
) {
    composable<PointHistoryRoute> {
        PointHistoryScreen(
            onBackClick = onNavigateBack,
        )
    }
}
