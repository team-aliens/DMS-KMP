package team.aliens.dms.kmp.feature.latestudy.navigation

import androidx.compose.runtime.Composable
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.latestudy.ui.LateStudyScreen

@Composable
fun LateStudyRoute(
    onBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    LateStudyScreen(
        onBack = onBack,
        onShowSnackBar = onShowSnackBar,
    )
}
