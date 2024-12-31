package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.viewmodel.SetPasswordSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.SetPasswordState
import team.aliens.dms.kmp.feature.signup.viewmodel.SetPasswordViewModel

@Composable
internal fun SetPassword(
    onBackPressed: () -> Unit,
    navigateToTerms: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: SetPasswordViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is SetPasswordSideEffect.MoveToTerms -> {
                    navigateToTerms(
                        signUpData.copy(password = it.password),
                    )
                }
            }
        }
    }

    SetPasswordScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onPasswordChange = viewModel::setPassword,
        onCheckPasswordChange = viewModel::setPasswordCheck,
    )
}

@Composable
private fun SetPasswordScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: SetPasswordState,
    onPasswordChange: (String) -> Unit,
    onCheckPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
    }
}
