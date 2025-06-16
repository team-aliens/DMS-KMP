package team.aliens.dms.kmp.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import team.aliens.dms.kmp.core.common.ui.startPadding
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
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInSideEffect
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInState
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInViewModel

@Composable
internal fun SignIn(
    navigateToMain: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToFindId: () -> Unit,
    navigateToFindPassword: () -> Unit,
    onShowSnackBar: (DmsSnackBarType,String) -> Unit,
) {
    val viewModel: SignInViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SignInSideEffect.NavigateToMain -> navigateToMain()
                is SignInSideEffect.ShowSnackBar -> onShowSnackBar(it.snackBarType,it.message)
            }
        }
    }

    SignInScreen(
        onSignInClick = viewModel::signIn,
        navigateToSignUp = navigateToSignUp,
        state = state,
        onAccountIdChange = viewModel::setAccountId,
        onPasswordChange = viewModel::setPassword,
    )
}

@Composable
private fun SignInScreen(
    onSignInClick: () -> Unit,
    navigateToSignUp: () -> Unit,
    state: SignInState,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(title = "로그인")
        DmsSymbol(
            modifier = Modifier
                .startPadding(24.dp)
                .topPadding(40.dp),
        )
        UserInformationInputs(
            modifier = Modifier.topPadding(44.dp),
            accountId = state.accountId,
            onAccountIdChange = onAccountIdChange,
            password = state.password,
            onPasswordChange = onPasswordChange,
            onFindId = {},
            onResetPassword = {},
        )
        Spacer(modifier = Modifier.weight(1f))
        SignupActions(onSignUp = navigateToSignUp)
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            text = "로그인",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = onSignInClick,
            enabled = state.buttonEnabled,
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
        modifier = modifier.horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(36.dp),
    ) {
        DmsTextField(
            modifier = Modifier.fillMaxWidth(),
            value = accountId,
            hint = "아이디",
            onValueChange = onAccountIdChange,
        )
        DmsTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            hint = "비빌번호",
            onValueChange = onPasswordChange,
            showVisibleIcon = true,
        )
        Row(
            modifier = Modifier.align(Alignment.End),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DmsText(
                modifier = Modifier.clickable(
                    onClick = onFindId,
                ),
                text = "아이디 찾기",
                style = DmsTypography.Body3,
                color = DmsTheme.colors.inverseSurface,
            )
            VerticalDivider(
                modifier = Modifier.height(12.dp),
                thickness = 0.5.dp,
                color = DmsTheme.colors.inverseSurface,
            )
            DmsText(
                modifier = Modifier
                    .clickable(
                        onClick = onResetPassword,
                    ),
                text = "비밀번호 재설정",
                style = DmsTypography.Body3,
                color = DmsTheme.colors.inverseSurface,
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
            space = 12.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsText(
            text = "아직 회원이 아니신가요?",
            style = DmsTypography.Caption,
            color = DmsTheme.colors.inverseSurface,
        )
        DmsText(
            modifier = Modifier.clickable(onClick = onSignUp),
            text = "회원가입",
            style = DmsTypography.Body1,
            color = DmsTheme.colors.onSecondary,
        )
    }
}
