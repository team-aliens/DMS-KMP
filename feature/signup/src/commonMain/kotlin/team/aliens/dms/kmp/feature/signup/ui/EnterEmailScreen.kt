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
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailViewModel

@Composable
internal fun EnterEmail(
    onBackPressed: () -> Unit,
    navigateToEnterEmailVerificationCode: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: EnterEmailViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is EnterEmailSideEffect.MoveToEnterEmailVerificationCode -> {
                    navigateToEnterEmailVerificationCode(signUpData.copy(email = it.email))
                }
            }
        }
    }

    EnterEmailScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onEmailChange = viewModel::setEmail,
    )
}

@Composable
private fun EnterEmailScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterEmailState,
    onEmailChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
    }
}
