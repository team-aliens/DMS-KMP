package team.aliens.dms.kmp.feature.editpassword.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun CheckPassword(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: CheckPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is CheckPasswordSideEffect.SuccessCheckPassword -> onNavigateResetPassword(state.currentPassword)

                is CheckPasswordSideEffect.FailCheckPassword -> onShowSnackBar(
                    DmsSnackBarType.ERROR, it.message
                )
            }
        }
    }

    CheckPasswordScreen(
        onBackPressed = onBackPressed,
        onResetPasswordClick = viewModel::resetPassword,
        state = state,
        onPasswordChange = viewModel::setPassword,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun CheckPasswordScreen(
    onBackPressed: () -> Unit,
    onResetPasswordClick: () -> Unit,
    state: CheckPasswordState,
    onPasswordChange: (String) -> Unit,
    onClearFocus: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colorScheme.surfaceTint)
            .systemBarsPadding()
            .pointerInput(Unit) { // TODO KMP 구현
                detectTapGestures(
                    onTap = { onClearFocus() }
                )
            },
    ) {
        DmsTopAppBar(onBackPressed = onBackPressed)
        DmsSymbolContent(
            modifier = Modifier
                .topPadding(52.dp),
            title = "비밀번호 확인",
            description = "기존 비밀번호를 입력해주세요",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .horizontalPadding(24.dp),
            label = "비밀번호",
            value = state.currentPassword,
            hint = "비밀번호 입력",
            onValueChange = onPasswordChange,
            showVisibleIcon = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onResetPasswordClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}
