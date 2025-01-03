package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.numberfield.DmsNumberField
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.timer.DmsTimer
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeViewModel

const val EMAIL_VERIFICATION_CODE_LENGTH = 6

@Composable
internal fun EnterEmailVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: EnterEmailVerificationCodeViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber -> {
                    navigateToEnterStudentNumber(signUpData.copy(authCode = it.authCode))
                }
            }
        }
    }

    EnterEmailVerificationCodeScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onEmailVerificationCodeChange = viewModel::setEmailVerificationCode,
        onTimerFinished = viewModel::setTimerFinished,
    )
}

@Composable
private fun EnterEmailVerificationCodeScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterEmailVerificationCodeState,
    onEmailVerificationCodeChange: (String) -> Unit,
    onTimerFinished: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        EmailVerificationCodeInfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .startPadding(24.dp)
                .topPadding(48.dp),
            onTimerFinished = onTimerFinished,
        )
        DmsNumberField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            totalLength = EMAIL_VERIFICATION_CODE_LENGTH,
            value = state.emailVerificationCode,
            onValueChange = onEmailVerificationCodeChange,
        )
        DmsButton(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .topPadding(20.dp),
            text = "인증코드 재발송",
            buttonType = ButtonType.Text,
            buttonColor = ButtonColor.Primary,
            onClick = { },
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = onNextClick,
            enabled = state.buttonEnabled,
        )
    }
}

@Composable
private fun EmailVerificationCodeInfoBanner(
    modifier: Modifier = Modifier,
    onTimerFinished: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DmsText(
            text = "이메일 인증번호를 입력해주세요",
            style = DmsTypography.Header3,
        )
        Row {
            DmsText(
                text = "이메일로 전송 번호 6자리를 ",
                style = DmsTypography.Body1,
                color = DmsTheme.colors.inverseOnSurface,
            )
            DmsTimer { onTimerFinished(it) }
            DmsText(
                text = " 내로 입력해주세요.",
                style = DmsTypography.Body1,
                color = DmsTheme.colors.inverseOnSurface,
            )
        }
    }
}
