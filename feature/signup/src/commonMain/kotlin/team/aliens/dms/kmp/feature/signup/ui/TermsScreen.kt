package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsState
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsViewModel

@Composable
internal fun Terms(
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
) {
    val viewModel: TermsViewModel = koinInject()
    val state by viewModel.state.collectAsState()
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    TermsScreen(
        onBackPressed = onBackPressed,
        navigateToSignIn = navigateToSignIn,
        termsUrl = termsUrl,
        state = state,
        onAllAgreeButtonClick = viewModel::setButtonEnabled,
        theme = theme,
    )
}

@Composable
private fun TermsScreen(
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
    state: TermsState,
    onAllAgreeButtonClick: (Boolean) -> Unit,
    theme: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {

    }
}
