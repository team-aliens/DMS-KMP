package team.aliens.dms.kmp.feature.signup.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
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
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField
import team.aliens.dms.kmp.core.model.signup.SignUpData
import team.aliens.dms.kmp.feature.signup.component.SignUpInfoBanner
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterStudentNumberSideEffect
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterStudentNumberState
import team.aliens.dms.kmp.feature.signup.viewmodel.EnterStudentNumberViewModel

@Composable
internal fun EnterStudentNumber(
    onBackPressed: () -> Unit,
    navigateToSetId: (SignUpData) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: EnterStudentNumberViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is EnterStudentNumberSideEffect.MoveToSetId -> navigateToSetId(effect.signUpData)

                is EnterStudentNumberSideEffect.ShowConflictSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "이미 가입된 학번이에요",
                )

                is EnterStudentNumberSideEffect.ShowErrorSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "학번을 확인해주세요",
                )
            }
        }
    }

    EnterStudentNumberScreen(
        onBackPressed = onBackPressed,
        onNextClick = viewModel::onNextClick,
        state = state,
        onGradeChange = viewModel::setGrade,
        onClassroomChange = viewModel::setClassRoom,
        onNumberChange = viewModel::setNumber,
    )
}

@Composable
private fun EnterStudentNumberScreen(
    onBackPressed: () -> Unit,
    onNextClick: () -> Unit,
    state: EnterStudentNumberState,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
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
                .startPadding(24.dp)
                .topPadding(20.dp),
            title = "학번 입력",
            description = "숫자만 입력해주세요.",
        )
        StudentNumberInputs(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalPadding(24.dp)
                .topPadding(44.dp),
            grade = state.grade,
            classroom = state.classroom,
            number = state.number,
            onGradeChange = onGradeChange,
            onClassroomChange = onClassroomChange,
            onNumberChange = onNumberChange,
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
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun StudentNumberInputs(
    modifier: Modifier = Modifier,
    grade: String,
    classroom: String,
    number: String,
    onGradeChange: (String) -> Unit,
    onClassroomChange: (String) -> Unit,
    onNumberChange: (String) -> Unit,
) {
    val classroomFocusRequest = remember { FocusRequester() }
    val numberFocusRequest = remember { FocusRequester() }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsTextField(
            modifier = Modifier.weight(1f),
            value = grade,
            onValueChange = { grade ->
                onGradeChange(grade)
                if (grade.isNotEmpty()) classroomFocusRequest.requestFocus()
            },
            label = "학년",
            hint = "학년 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(classroomFocusRequest),
            value = classroom,
            onValueChange = { classroom ->
                onClassroomChange(classroom)
                if (classroom.isNotEmpty()) numberFocusRequest.requestFocus()
            },
            label = "반",
            hint = "반 입력",
            keyboardType = KeyboardType.Number,
        )
        DmsTextField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(numberFocusRequest),
            value = number,
            onValueChange = onNumberChange,
            label = "번호",
            hint = "번호 입력",
            keyboardType = KeyboardType.Number,
        )
    }
}
