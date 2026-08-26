package team.aliens.dms.kmp.feature.latestudy.di

import androidx.compose.runtime.Composable
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.latestudy.ui.LateStudyScreen
import team.aliens.dms.kmp.feature.latestudy.viewmodel.LateStudyViewModel

val lateStudyModule = module {
    viewModelOf(::LateStudyViewModel)
}

@Composable
fun LateStudyRoute(
    onBack: () -> Unit,
    onSubmitted: () -> Unit = {},
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    LateStudyScreen(
        onBack = onBack,
        onSubmitted = onSubmitted,
        onShowSnackBar = onShowSnackBar,
    )
}
