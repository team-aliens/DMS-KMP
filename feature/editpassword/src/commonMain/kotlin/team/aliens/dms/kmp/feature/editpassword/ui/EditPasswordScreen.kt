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
internal fun EditPassword(
    onBackPressed: () -> Unit,
    currentPassword: String,
    onNavigateSetting: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EditPasswordViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is EditPasswordSideEffect.SuccessEditPassword -> onNavigateSetting()
                is EditPasswordSideEffect.FailEditPassword -> onShowSnackBar(
                    DmsSnackBarType.ERROR, it.message
                )
                is EditPasswordSideEffect.PasswordMismatch -> onShowSnackBar(
                    DmsSnackBarType.ERROR, it.message
                )
            }
        }
    }

    EditPasswordScreen(
        onBackPressed = onBackPressed,
        onEditPasswordClick = { viewModel.EditPassword(currentPassword) },
        state = state,
        onNewPasswordChange = viewModel::setNewPassword,
        onCheckNewPasswordChange = viewModel::setCheckNewPassword,
        onClearFocus = { focusManager.clearFocus() },
    )
}

@Composable
private fun EditPasswordScreen(
    onBackPressed: () -> Unit,
    onEditPasswordClick: () -> Unit,
    state: EditPasswordState,
    onNewPasswordChange: (String) -> Unit,
    onCheckNewPasswordChange: (String) -> Unit,
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
            title = "비밀번호 재설정",
            description = "비밀번호를 다시 설정해주세요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .horizontalPadding(24.dp),
            label = "비밀번호",
            value = state.newPassword,
            hint = "비밀번호 입력",
            onValueChange = onNewPasswordChange,
            showVisibleIcon = true,
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
                .horizontalPadding(24.dp),
            label = "비밀번호 재입력",
            value = state.checkNewPassword,
            hint = "비밀번호 재입력",
            onValueChange = onCheckNewPasswordChange,
            showVisibleIcon = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "완료",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onEditPasswordClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}
