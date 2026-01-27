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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.feature.editpassword.viewmodel.CheckPasswordSideEffect
import team.aliens.dms.kmp.feature.editpassword.viewmodel.CheckPasswordState
import team.aliens.dms.kmp.feature.editpassword.viewmodel.CheckPasswordViewModel

@Composable
internal fun CheckPassword(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: CheckPasswordViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
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
        onResetPasswordClick = viewModel::checkPassword,
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
            .background(DmsTheme.colors.surfaceTint)
            .systemBarsPadding()
            .pointerInput(Unit) { // TODO KMP 구현
                detectTapGestures(
                    onTap = { onClearFocus() }
                )
            },
    ) {
        DmsTopAppBar(onBackPressed = onBackPressed)
        DmsSymbol(
            modifier = Modifier
                .topPadding(52.dp)
                .horizontalPadding(24.dp),
        )
        DmsText(
            modifier = Modifier
                .padding(top = 20.dp)
                .horizontalPadding(24.dp),
            text = "비밀번호 확인",
            style = DmsTypography.TitleB,
            color = DmsTheme.colors.onTertiaryContainer,
        )
        DmsText(
            modifier = Modifier
                .padding(top = 4.dp)
                .horizontalPadding(24.dp),
            text = "기존 비밀번호를 입력해주세요",
            style = DmsTypography.BodyM,
            color = DmsTheme.colors.onSurfaceVariant,
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
