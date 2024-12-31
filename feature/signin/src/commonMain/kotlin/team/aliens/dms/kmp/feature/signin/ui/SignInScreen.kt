package team.aliens.dms.kmp.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInState
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInViewModel

@Composable
internal fun SignIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToFindPassword: () -> Unit,
) {
    val viewModel: SignInViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    SignInScreen(
        navigateToMain = navigateToMain,
        navigateToSignUp = navigateToSignUp,
        state = state,
        onAccountIdChange = viewModel::setAccountId,
        onPasswordChange = viewModel::setPassword,
    )
}

@Composable
fun SignInScreen(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    state: SignInState,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
    }
}
