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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.PaddingDefaults
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsLargeTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
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
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = "비밀번호를 입력해주세요",
            description = "비밀번호는 영문, 숫자, 기호를 포함한 8~20자로 입력해주세요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(PaddingDefaults.ExtraLarge)
                .horizontalPadding(),
            value = state.password,
            onValueChange = onPasswordChange,
            hint = "비밀번호를 입력해주세요",
            label = "비밀번호 입력",
            showVisibleIcon = true,
            isError = state.showPasswordDescription,
            errorDescription = "비밀번호는 영문, 숫자, 기호를 포함한 8~20자로 설정해주세요.",
            keyboardType = KeyboardType.Password,
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(36.dp)
                .horizontalPadding(),
            value = state.passwordCheck,
            onValueChange = onCheckPasswordChange,
            hint = "비밀번호를 다시 입력해주세요",
            label = "비밀번호 확인",
            showVisibleIcon = true,
            isError = state.showCheckPasswordDescription,
            errorDescription = "비밀번호가 일치하지 않습니다. 다시 입력해주세요.",
            keyboardType = KeyboardType.Password,
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
