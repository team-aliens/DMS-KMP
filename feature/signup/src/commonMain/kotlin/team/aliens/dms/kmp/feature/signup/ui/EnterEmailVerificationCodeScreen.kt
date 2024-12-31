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
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeViewModel

@Composable
internal fun EnterEmailVerificationCode(
    onBackPressed: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: EnterEmailVerificationCodeViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is EnterEmailVerificationCodeSideEffect.MoveToSetId -> {
                    navigateToSetId(signUpData.copy(authCode = it.authCode))
                }
            }
        }
    }

    EnterEmailVerificationCodeScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onEmailVerificationCodeChange = viewModel::setEmailVerificationCode,
    )
}

@Composable
private fun EnterEmailVerificationCodeScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterEmailVerificationCodeState,
    onEmailVerificationCodeChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
    }
}
