package team.aliens.dms.kmp.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.VerticalDivider
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
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInSideEffect
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInState
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInViewModel

@Composable
internal fun SignIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToResetPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SignInViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SignInSideEffect.NavigateToMain -> navigateToMain()
                SignInSideEffect.NavigateToFindId -> navigateToFindId()
                SignInSideEffect.NavigateToResetPassword -> navigateToResetPassword()
                is SignInSideEffect.ShowSnackBar -> onShowSnackBar(effect.snackBarType, effect.message)
            }
        }
    }

    SignInScreen(
        onSignInClick = viewModel::signIn,
        navigateToSignUp = navigateToSignUp,
        state = state,
        onAccountIdChange = viewModel::setAccountId,
        onPasswordChange = viewModel::setPassword,
        onFindIdClick = viewModel::navigateFindId,
        onResetPasswordClick = viewModel::navigateResetPassword,
    )
}

@Composable
private fun SignInScreen(
    onSignInClick: () -> Unit,
    navigateToSignUp: () -> Unit,
    state: SignInState,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onFindIdClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsSymbol(
            modifier = Modifier
                .topPadding(52.dp)
                .horizontalPadding(24.dp),
        )
        DmsText(
            modifier = Modifier
                .padding(top = 20.dp)
                .horizontalPadding(24.dp),
            text = "로그인",
            style = DmsTypography.TitleB,
            color = DmsTheme.colors.onTertiaryContainer,
        )
        UserInformationInputs(
            modifier = Modifier
                .topPadding(48.dp)
                .horizontalPadding(24.dp),
            accountId = state.accountId,
            onAccountIdChange = onAccountIdChange,
            password = state.password,
            onPasswordChange = onPasswordChange,
            onFindId = onFindIdClick,
            onResetPassword = onResetPasswordClick,
        )
        Spacer(modifier = Modifier.weight(1f))
        SignupActions(onSignUp = navigateToSignUp)
        DmsButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "로그인",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onSignInClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun UserInformationInputs(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onFindId: () -> Unit,
    onResetPassword: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        DmsTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "아이디",
            value = accountId,
            hint = "아이디 입력",
            onValueChange = onAccountIdChange,
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            label = "비밀번호",
            value = password,
            hint = "비밀번호 입력",
            onValueChange = onPasswordChange,
            showVisibleIcon = true,
        )
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DmsButton(
                text = "아이디 찾기",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = onFindId,
            )
            VerticalDivider(
                modifier = Modifier.height(12.dp),
                thickness = 1.dp,
                color = DmsTheme.colors.inverseSurface,
            )
            DmsButton(
                text = "비밀번호 재설정",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = onResetPassword,
            )
        }
    }
}

@Composable
private fun SignupActions(
    modifier: Modifier = Modifier,
    onSignUp: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsText(
            text = "아직 회원이 아니신가요?",
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseSurface,
        )
        DmsButton(
            text = "회원가입 하러가기",
            buttonType = ButtonType.Underline,
            buttonColor = ButtonColor.Gray,
            onClick = onSignUp,
        )
    }
}
