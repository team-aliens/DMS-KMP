package team.aliens.dms.kmp.feature.notification.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.type.PointType
import team.aliens.dms.kmp.feature.notification.ui.Notification

@Serializable
data object NotificationRoute

fun NavController.navigateToNotification(
    navOptions: NavOptions? = null,
) = navigate(
    route = NotificationRoute,
    navOptions = navOptions,
)

fun NavGraphBuilder.notification(
    onNavigateBack: () -> Unit,
    onNavigateNotificationDetailClick: (String) -> Unit,
    onNavigatePointHistory: (PointType) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit
) {
    composable<NotificationRoute> {
        Notification(
            onNavigateBack = onNavigateBack,
            onNavigateNotificationDetailClick = onNavigateNotificationDetailClick,
            onNavigatePointHistory = onNavigatePointHistory,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
