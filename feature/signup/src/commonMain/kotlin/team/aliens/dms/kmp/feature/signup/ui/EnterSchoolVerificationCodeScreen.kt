package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.PaddingDefaults
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsLargeTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.numberfield.DmsNumberField
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeViewModel

const val VERIFICATION_CODE_LENGTH = 6

@Composable
internal fun EnterSchoolVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
) {
    val viewModel: EnterSchoolVerificationCodeViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion -> {
                    navigateToEnterSchoolVerificationQuestion(SignUpData(schoolCode = it.schoolCode))
                }
            }
        }
    }

    EnterSchoolVerificationCodeScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onVerificationCodeChange = viewModel::setVerificationCode,
    )
}

@Composable
private fun EnterSchoolVerificationCodeScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterSchoolVerificationCodeState,
    onVerificationCodeChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = "인증코드를 입력해주세요",
            description = "학교 인증코드를 입력해주세요.",
        )
        DmsNumberField(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(PaddingDefaults.ExtraLarge)
                .horizontalPadding(),
            value = state.verificationCode,
            onValueChange = onVerificationCodeChange,
            totalLength = VERIFICATION_CODE_LENGTH,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding()
                .bottomPadding(),
            text = "다음",
            onClick = onNextClick,
            enabled = state.buttonEnabled,
        )
    }
}
