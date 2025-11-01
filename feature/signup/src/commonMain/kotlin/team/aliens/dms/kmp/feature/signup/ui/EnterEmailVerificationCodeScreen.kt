package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.numberfield.DmsNumberField
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.timer.DmsTimer
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterEmailVerificationCodeViewModel

const val EMAIL_VERIFICATION_CODE_LENGTH = 6

@Composable
internal fun EnterEmailVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterStudentNumber: (SignUpData) -> Unit,
) {
    val viewModel: EnterEmailVerificationCodeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterEmailVerificationCodeSideEffect.MoveToEnterStudentNumber -> {
                    navigateToEnterStudentNumber(effect.signUpData)
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
            .background(DmsTheme.colors.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        DmsSymbol(
            modifier = Modifier
                .horizontalPadding(24.dp)
                .topPadding(4.dp),
        )
        EmailVerificationCodeInfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(20.dp),
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
            buttonType = ButtonType.Underline,
            buttonColor = ButtonColor.Gray,
            onClick = { },
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
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
            text = "이메일 인증",
            style = DmsTypography.TitleB,
            color = DmsTheme.colors.onTertiaryContainer,
        )
        Column {
            DmsText(
                text = "이메일로 전송된 ",
                style = DmsTypography.BodyM,
                color = DmsTheme.colors.inverseSurface,
            )
            Row {
                DmsText(
                    text = "인증번호 6자리를 ",
                    style = DmsTypography.BodyM,
                    color = DmsTheme.colors.inverseSurface,
                )
                DmsTimer { onTimerFinished(it) }
                DmsText(
                    text = " 내로 입력해주세요.",
                    style = DmsTypography.BodyM,
                    color = DmsTheme.colors.inverseSurface,
                )
            }
        }
    }
}
