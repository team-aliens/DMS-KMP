package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject
import team.aliens.dms.kmp.core.common.ui.bottomPadding
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.designsystem.appbar.DmsLargeTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.signup.model.SignUpData
import team.aliens.dms.kmp.feature.signup.viewmodel.SetIdSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.SetIdViewModel

@Composable
internal fun SetId(
    onBackPressed: () -> Unit,
    navigateToSetPassword: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: SetIdViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                is SetIdSideEffect.MoveToSetPassword -> {
                    navigateToSetPassword(
                        signUpData.copy(
                            accountId = it.id,
                            grade = 1,
                            classRoom = 1,
                            number = 1,
                        ),
                    )
                }
            }
        }
    }

    SetIdScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
    )
}

@Composable
private fun SetIdScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = "아이디 입력",
            description = "아이디를 입력해주세요.",
        )
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding()
                .bottomPadding(),
            text = "다음",
            onClick = onNextClick,
        )
    }
}
