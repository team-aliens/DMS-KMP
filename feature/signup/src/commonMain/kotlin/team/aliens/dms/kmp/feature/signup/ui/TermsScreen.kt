package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
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
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.checkbox.DmsCheckbox
import team.aliens.dms.kmp.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.webview.DmsWebView
import team.aliens.dms.kmp.feature.signup.component.SignUpInfoBanner
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsState
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsViewModel

@Composable
internal fun Terms(
    onBackPressed: () -> Unit,
    navigateToComplete: () -> Unit,
    webViewUrl: String,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: TermsViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TermsSideEffect.NavigateToComplete -> navigateToComplete()
                is TermsSideEffect.FailSignUp -> onShowSnackBar(DmsSnackBarType.ERROR, "다시 시도해주세요")
            }
        }
    }

    TermsScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::postSignUp,
        webViewUrl = webViewUrl,
        state = state,
        onAgreeButtonClick = viewModel::setButtonEnabled,
        theme = theme,
    )
}

@Composable
private fun TermsScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    webViewUrl: String,
    state: TermsState,
    onAgreeButtonClick: (Boolean) -> Unit,
    theme: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DmsTopAppBar(
            title = "회원가입",
            onBackPressed = onBackPressed,
        )
        DmsSymbol(
            modifier = Modifier
                .horizontalPadding(24.dp)
                .topPadding(4.dp),
        )
        SignUpInfoBanner(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(20.dp)
                .horizontalPadding(24.dp)
                .topPadding(48.dp),
            title = "약관 동의",
            description = "동의 후 DMS를 사용할 수 있습니다.",
        )
        DmsWebView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .horizontalPadding(24.dp)
                .topPadding(12.dp),
            url = "$webViewUrl/policy/privacy?theme=$theme",
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.background,
        )
        AgreeCheckBox(
            modifier = Modifier.fillMaxWidth(),
            isCheck = state.buttonEnabled,
            onAgreeButtonClick = onAgreeButtonClick,
        )
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onNextClick,
            enabled = state.buttonEnabled,
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun AgreeCheckBox(
    modifier: Modifier = Modifier,
    isCheck: Boolean,
    onAgreeButtonClick: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterHorizontally,
            space = 12.dp,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsText(
            text = "전체 약관 동의",
            style = DmsTypography.BodyM,
            color = DmsTheme.colors.tertiaryContainer,
        )
        DmsCheckbox(
            checked = isCheck,
            onCheckedChange = onAgreeButtonClick,
        )
    }
}
