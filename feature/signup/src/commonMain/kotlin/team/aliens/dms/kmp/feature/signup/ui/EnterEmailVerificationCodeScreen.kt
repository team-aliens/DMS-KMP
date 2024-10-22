package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.PaddingDefaults
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsLargeTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.numberfield.DmsNumberField
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.timer.DmsTimer
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
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = "인증코드를 입력해주세요",
            description = "입력하신 이메일 주소로 인증코드가 전송되었어요.",
        )
        VerificationCode(
            emailVerificationCode = state.emailVerificationCode,
            onEmailVerificationCodeChange = onEmailVerificationCodeChange,
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

@Composable
fun VerificationCode(
    modifier: Modifier = Modifier,
    emailVerificationCode: String,
    onEmailVerificationCodeChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .topPadding(PaddingDefaults.ExtraLarge)
            .horizontalPadding(),
    ) {
        DmsTimer()
        Spacer(modifier = Modifier.height(PaddingDefaults.Large))
        DmsNumberField(
            modifier = Modifier.fillMaxWidth(),
            value = emailVerificationCode,
            onValueChange = onEmailVerificationCodeChange,
            totalLength = 6,
        )
        Spacer(modifier = Modifier.height(PaddingDefaults.ExtraLarge))
        Row(
            modifier = Modifier.clickable(
                onClick = { },
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                painter = painterResource(DmsIcon.Refresh),
                contentDescription = "refresh",
                tint = DmsTheme.colors.secondary,
            )
            DmsText(
                text = "인증문자 재발송",
                style = DmsTypography.Body1Medium,
                color = DmsTheme.colors.secondary,
            )
        }
    }
}
