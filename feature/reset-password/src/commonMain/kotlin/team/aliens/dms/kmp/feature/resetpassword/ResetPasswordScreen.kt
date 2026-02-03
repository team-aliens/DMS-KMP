package team.aliens.dms.kmp.feature.resetpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.common.timer.CountDownTimer
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.message.TextFieldError
import team.aliens.dms.kmp.core.ui.walkthrough.WalkThroughState
import team.aliens.dms.kmp.core.ui.walkthrough.WalkThroughTemplate
import team.aliens.dms.kmp.core.ui.walkthrough.rememberWalkThroughState
import team.aliens.dms.kmp.feature.resetpassword.component.EmailVerificationContent
import team.aliens.dms.kmp.feature.resetpassword.component.InputIdContent
import team.aliens.dms.kmp.feature.resetpassword.component.InputNewPasswordContent
import team.aliens.dms.kmp.feature.resetpassword.component.InputUserInfoContent

internal const val EMAIL_VERIFICATION_CODE_LENGTH = 6

internal enum class ResetPasswordStep {
    InputId,
    InputUserInfo,
    InputEmailVerificationCode,
    InputNewPassword,
}

@Composable
internal fun ResetPasswordScreen(
    onNavigateBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: ResetPasswordViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val walkThroughState =
        rememberWalkThroughState<ResetPasswordStep>(onNavigateUp = onNavigateBack)
    val countdownTimer = remember { CountDownTimer() }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is ResetPasswordSideEffect.NavigateUp -> onNavigateBack()
                is ResetPasswordSideEffect.ResetCountDownTimer -> countdownTimer.restart()
                is ResetPasswordSideEffect.MoveToNext -> walkThroughState.next()
                is ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "존재하지 않는 계정 아이디입니다.",
                )

                is ResetPasswordSideEffect.ShowTooManyRequestSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "요청이 너무 많습니다. 잠시 후 다시 시도해주세요.",
                )

                is ResetPasswordSideEffect.ShowServerErrorSnackBar -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                )

                is ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar -> onShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "이메일 인증 코드가 발송되었습니다.",
                )

                is ResetPasswordSideEffect.ShowPasswordResetSuccessSnackBar -> onShowSnackBar(
                    DmsSnackBarType.SUCCESS,
                    "비밀번호가 변경되었습니다.",
                )
            }
        }
    }

    LaunchedEffect(walkThroughState.currentType) {
        viewModel.changePage(walkThroughState.currentType)
    }

    ResetPasswordScreen(
        walkThroughState = walkThroughState,
        countDownTimer = countdownTimer,
        accountId = state.accountId,
        name = state.name,
        email = state.email,
        hashEmail = state.hashEmail,
        emailVerificationCode = state.emailVerificationCode,
        password = state.password,
        passwordConfirm = state.confirmPassword,
        buttonEnabled = state.buttonEnabled,
        isLoading = state.isLoading,
        isResendLoading = state.isResendLoading,
        emailVerificationCodeTextFieldError = state.emailVerificationCodeTextFieldError,
        passwordTextFieldError = state.passwordTextFieldError,
        confirmPasswordTextFieldError = state.confirmPasswordTextFieldError,
        onAccountIdChanged = viewModel::setAccountId,
        onNameChange = viewModel::setName,
        onEmailChange = viewModel::setEmail,
        onEmailVerificationCodeChange = viewModel::setEmailVerificationCode,
        onResendEmailVerificationCode = viewModel::resendEmailVerificationCode,
        onEmailVerificationTimerFinished = viewModel::setEmailVerificationTimerFinished,
        onPasswordChange = viewModel::setPassword,
        onPasswordConfirmChange = viewModel::setConfirmPassword,
        onBackClick = walkThroughState::previous,
        onContinueClick = viewModel::moveNext,
    )
}

@Composable
private fun ResetPasswordScreen(
    walkThroughState: WalkThroughState<ResetPasswordStep> = WalkThroughState(),
    countDownTimer: CountDownTimer,
    accountId: String,
    name: String,
    email: String,
    hashEmail: String,
    emailVerificationCode: String,
    password: String,
    passwordConfirm: String,
    buttonEnabled: Boolean,
    isLoading: Boolean,
    isResendLoading: Boolean,
    emailVerificationCodeTextFieldError: TextFieldError,
    passwordTextFieldError: TextFieldError,
    confirmPasswordTextFieldError: TextFieldError,
    onAccountIdChanged: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onEmailVerificationCodeChange: (String) -> Unit,
    onResendEmailVerificationCode: () -> Unit,
    onEmailVerificationTimerFinished: (Boolean) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onContinueClick: (ResetPasswordStep?) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.surfaceTint)
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            DmsTopAppBar(onBackPressed = onBackClick)
        },
        containerColor = DmsTheme.colors.surfaceTint,
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            WalkThroughTemplate(
                modifier = Modifier.weight(1f),
                state = walkThroughState,
            ) {
                step(ResetPasswordStep.InputId) {
                    InputIdContent(
                        accountId = accountId,
                        onAccountIdChange = onAccountIdChanged,
                    )
                }
                step(ResetPasswordStep.InputUserInfo) {
                    InputUserInfoContent(
                        name = name,
                        email = email,
                        hashEmail = hashEmail,
                        onNameChange = onNameChange,
                        onEmailChange = onEmailChange,
                    )
                }
                step(ResetPasswordStep.InputEmailVerificationCode) {
                    EmailVerificationContent(
                        countDownTimer = countDownTimer,
                        email = email,
                        emailVerificationCode = emailVerificationCode,
                        isResendLoading = isResendLoading,
                        textFieldError = emailVerificationCodeTextFieldError,
                        onEmailVerificationCodeChange = onEmailVerificationCodeChange,
                        onResendCode = onResendEmailVerificationCode,
                        onTimerFinished = onEmailVerificationTimerFinished,
                    )
                }
                step(ResetPasswordStep.InputNewPassword) {
                    InputNewPasswordContent(
                        password = password,
                        passwordConfirm = passwordConfirm,
                        passwordTextFieldError = passwordTextFieldError,
                        passwordConfirmTextFieldError = confirmPasswordTextFieldError,
                        onPasswordChange = onPasswordChange,
                        onPasswordConfirmChange = onPasswordConfirmChange,
                    )
                }
            }
            DmsButton(
                modifier = Modifier.fillMaxWidth(),
                text = if (walkThroughState.isLastStep) "완료" else "다음",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                keyboardInteractionEnabled = true,
                onClick = { onContinueClick(walkThroughState.currentType) },
                enabled = buttonEnabled,
                isLoading = isLoading,
            )
        }
    }
}
