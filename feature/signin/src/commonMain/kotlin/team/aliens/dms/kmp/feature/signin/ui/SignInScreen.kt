package team.aliens.dms.kmp.feature.signin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.PaddingDefaults
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.button.DmsOutlineButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInState
import team.aliens.dms.kmp.feature.signin.viewmodel.SignInViewModel

@Composable
internal fun SignIn() {
    val viewModel: SignInViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    SignInScreen(
        state = state,
        onAccountIdChange = viewModel::setAccountId,
        onPasswordChange = viewModel::setPassword,
    )
}

@Composable
fun SignInScreen(
    state: SignInState,
    onAccountIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsSymbol(
            modifier = Modifier
                .topPadding(36.dp)
                .horizontalPadding(),
        )
        UserInformationInputs(
            modifier = Modifier.topPadding(PaddingDefaults.Large),
            accountId = state.accountId,
            onAccountIdChange = onAccountIdChange,
            password = state.password,
            onPasswordChange = onPasswordChange,
        )
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(28.dp)
                .horizontalPadding(),
            text = "로그인",
            enabled = state.buttonEnabled,
            onClick = { },
        )
        UnauthorizedActions(
            onFindId = { },
            onFindPassword = { },
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsOutlineButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding()
                .bottomPadding(16.dp),
            text = "회원가입 하기",
            onClick = { },
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
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .horizontalPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DmsTextField(
            value = accountId,
            onValueChange = onAccountIdChange,
            hint = "아이디를 입력해주세요",
            label = "아이디",
            showClearIcon = true,
        )
        DmsTextField(
            value = password,
            onValueChange = onPasswordChange,
            hint = "비밀번호를 입력해주세요",
            label = "비밀번호",
            keyboardType = KeyboardType.Password,
            showVisibleIcon = true,
        )
    }
}

@Composable
private fun UnauthorizedActions(
    modifier: Modifier = Modifier,
    onFindId: () -> Unit,
    onFindPassword: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .topPadding(PaddingDefaults.Large)
            .horizontalPadding(),
        horizontalArrangement = Arrangement.spacedBy(
            space = PaddingDefaults.Medium,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        DmsText(
            modifier = Modifier.clickable(
                onClick = onFindId,
            ),
            text = "아이디 찾기",
            style = DmsTypography.Body1Medium,
        )
        DmsText(
            text = "|",
            style = DmsTypography.Body1Medium,
        )
        DmsText(
            modifier = Modifier.clickable(
                onClick = onFindPassword,
            ),
            text = "비밀번호 찾기",
            style = DmsTypography.Body1Medium,
        )
    }
}
