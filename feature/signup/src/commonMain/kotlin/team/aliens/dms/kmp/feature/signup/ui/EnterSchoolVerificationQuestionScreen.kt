package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.component.SignUpInfoBanner
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterSchoolVerificationQuestionViewModel

@Composable
internal fun EnterSchoolVerificationQuestion(
    onBackPressed: () -> Unit,
    navigateToEnterEmail: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterSchoolVerificationQuestionViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterSchoolVerificationQuestionSideEffect.MoveToEnterEmail -> {
                    navigateToEnterEmail(effect.signUpData)
                }

                is EnterSchoolVerificationQuestionSideEffect.ShowErrorSnackBar -> onShowSnackBar(DmsSnackBarType.ERROR, "올바르지 않은 답변이에요")
                is EnterSchoolVerificationQuestionSideEffect.ShowQuestionErrorSnackBar -> onShowSnackBar(DmsSnackBarType.ERROR, "질문을 불러오지 못했어요")
            }
        }
    }

    EnterSchoolVerificationQuestionScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onVerificationAnswerChange = viewModel::setSchoolVerificationAnswer,
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
                .horizontalPadding(24.dp)
                .topPadding(20.dp),
            title = state.schoolVerificationQuestion,
            description = "학교 확인 질문이에요.",
        )
        DmsTextField(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            value = state.schoolVerificationAnswer,
            onValueChange = onVerificationAnswerChange,
            label = "답변",
            hint = "답변 입력",
            showClearIcon = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = "다음",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            keyboardInteractionEnabled = true,
            onClick = onNextClick,
            enabled = state.buttonEnabled,
        )
    }
}
