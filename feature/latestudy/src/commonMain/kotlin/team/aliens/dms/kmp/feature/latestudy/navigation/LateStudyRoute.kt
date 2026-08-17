package team.aliens.dms.kmp.feature.latestudy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.latestudy.ui.LateStudyScreen

@Serializable
data object LateStudyRoute

const val LATE_STUDY_STATUS_REFRESH_KEY = "late_study_status_refresh"

fun NavController.navigateToLateStudy() = navigate(LateStudyRoute)

fun NavGraphBuilder.lateStudy(
    onNavigateBack: () -> Unit,
    onSubmitted: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    composable<LateStudyRoute> {
        LateStudyScreen(
            onBack = onNavigateBack,
            onSubmitted = onSubmitted,
            onShowSnackBar = onShowSnackBar,
        )
    }
}
