package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.feature.signup.component.SignUpInfoBanner
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
        onPasswordCheckChange = viewModel::setPasswordCheck,
    )
}

@Composable
private fun SetPasswordScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: SetPasswordState,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
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
            title = "비밀번호를 입력해주세요",
            description = "영문, 숫자, 기호를 포함한 8~20자입니다.",
        )
        PasswordInputs(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            password = state.password,
            passwordCheck = state.passwordCheck,
            onPasswordChange = onPasswordChange,
            onPasswordCheckChange = onPasswordCheckChange,
            isPasswordFormatError = state.showPasswordDescription,
            isPasswordMatchError = state.showCheckPasswordDescription,
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
private fun PasswordInputs(
    modifier: Modifier = Modifier,
    password: String,
    passwordCheck: String,
    onPasswordChange: (String) -> Unit,
    onPasswordCheckChange: (String) -> Unit,
    isPasswordFormatError: Boolean,
    isPasswordMatchError: Boolean,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(44.dp),
    ) {
        DmsTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = "비밀번호",
            showVisibleIcon = true,
            isError = isPasswordFormatError,
            errorMessage = "형식이 일치하지 않습니다.",
        )
        DmsTextField(
            value = passwordCheck,
            onValueChange = onPasswordCheckChange,
            hint = "비밀번호 확인",
            showVisibleIcon = true,
            isError = isPasswordMatchError,
            errorMessage = "비밀번호가 일치하지 않습니다.",
        )
    }
}
