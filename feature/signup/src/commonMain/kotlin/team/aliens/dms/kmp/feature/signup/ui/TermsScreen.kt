package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.PaddingDefaults
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsLargeTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.webview.DmsWebView
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsState
import team.aliens.dms.kmp.feature.signup.viewmodel.TermsViewModel

@Composable
internal fun Terms(
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
) {
    val viewModel: TermsViewModel = koinInject()
    val state by viewModel.state.collectAsState()
    val theme = if (isSystemInDarkTheme()) {
        "dark"
    } else {
        "light"
    }

    TermsScreen(
        onBackPressed = onBackPressed,
        navigateToSignIn = navigateToSignIn,
        termsUrl = termsUrl,
        state = state,
        onAllAgreeButtonClick = viewModel::setButtonEnabled,
        theme = theme,
    )
}

@Composable
private fun TermsScreen(
    onBackPressed: () -> Unit,
    navigateToSignIn: () -> Unit,
    termsUrl: String,
    state: TermsState,
    onAllAgreeButtonClick: (Boolean) -> Unit,
    theme: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = "개인정보처리방침 안내",
            description = "개인정보처리방침을 확인해주세요.",
        )
        AllAgreeButton(
            buttonEnabled = state.buttonEnabled,
            onAllAgreeButtonClick = onAllAgreeButtonClick,
        )
        DmsWebView(
            modifier = Modifier
                .weight(1f)
                .topPadding(PaddingDefaults.Medium)
                .horizontalPadding()
                .clip(RoundedCornerShape(8.dp)),
            url = "$termsUrl/policy/privacy?theme=$theme",
        )
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(PaddingDefaults.ExtraLarge)
                .horizontalPadding()
                .bottomPadding(),
            text = "다음",
            onClick = navigateToSignIn,
            enabled = state.buttonEnabled,
        )
    }
}

@Composable
private fun AllAgreeButton(
    buttonEnabled: Boolean,
    onAllAgreeButtonClick: (Boolean) -> Unit,
) {
    var isCheck by remember { mutableStateOf(buttonEnabled) }
    val background = if (isCheck) {
        DmsTheme.colors.onSecondary
    } else {
        DmsTheme.colors.onBackground
    }
    val contentColor = if (isCheck) {
        DmsTheme.colors.surfaceContainerHighest
    } else {
        DmsTheme.colors.onSurface
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .topPadding(PaddingDefaults.ExtraLarge)
            .horizontalPadding()
            .background(
                color = background,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = {
                    isCheck = !isCheck
                    onAllAgreeButtonClick(isCheck)
                },
            )
            .padding(PaddingDefaults.Large),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            painter = painterResource(DmsIcon.Check),
            contentDescription = "check",
            tint = contentColor,
        )
        DmsText(
            text = "전체 동의",
            style = DmsTypography.SubtitleSemiBold,
            color = contentColor,
        )
    }
}
