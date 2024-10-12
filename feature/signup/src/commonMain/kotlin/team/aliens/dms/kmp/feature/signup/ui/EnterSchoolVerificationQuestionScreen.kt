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
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionViewModel

@Composable
internal fun EnterSchoolVerificationQuestion(
    onBackPressed: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    signUpData: SignUpData,
) {
    val viewModel: EnterSchoolVerificationQuestionViewModel = koinInject()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when(it) {
                is EnterSchoolVerificationQuestionSideEffect.MoveToSetId -> {
                    navigateToSetId(signUpData.copy(schoolAnswer = it.schoolAnswer))
                }
            }
        }
    }
    EnterSchoolVerificationQuestionScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onVerificationAnswerChange = viewModel::setSchoolVerificationAnswer
    )
}

@Composable
private fun EnterSchoolVerificationQuestionScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterSchoolVerificationQuestionState,
    onVerificationAnswerChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsLargeTopAppBar(
            onBackPressed = onBackPressed,
            title = state.schoolVerificationQuestion,
            description = "재학생이 맞는지 확인하는 단계입니다.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .topPadding(PaddingDefaults.Large)
                .horizontalPadding(),
            value = state.schoolVerificationAnswer,
            onValueChange = onVerificationAnswerChange,
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
