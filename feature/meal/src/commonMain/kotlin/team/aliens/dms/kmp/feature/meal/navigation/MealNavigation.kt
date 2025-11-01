package team.aliens.dms.kmp.feature.meal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.feature.meal.ui.MealScreen

@Serializable
data object MealRoute

fun NavController.navigateToMeal(
    navOptions: NavOptions? = null,
) = navigate(
    route = MealRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.meal(
    onNavigateToBack: () -> Unit,
) {
    composable<MealRoute> {
        MealScreen(onNavigateBack = onNavigateToBack)
    }
}
