package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.numberfield.DmsNumberField
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.component.SignUpInfoBanner
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationCodeViewModel

const val SCHOOL_VERIFICATION_CODE_LENGTH = 8

@Composable
internal fun EnterSchoolVerificationCode(
    onBackPressed: () -> Unit,
    navigateToEnterSchoolVerificationQuestion: (SignUpData) -> Unit,
) {
    val viewModel: EnterSchoolVerificationCodeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterSchoolVerificationCodeSideEffect.MoveToEnterSchoolVerificationQuestion -> {
                    navigateToEnterSchoolVerificationQuestion(SignUpData(schoolCode = effect.schoolCode))
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
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        SignUpInfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .startPadding(24.dp)
                .topPadding(48.dp),
            title = "학교 인증코드를 입력해주세요",
            description = "학교 인증코드는 8자리에요.",
        )
        DmsNumberField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            totalLength = SCHOOL_VERIFICATION_CODE_LENGTH,
            value = state.verificationCode,
            onValueChange = onVerificationCodeChange,
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
