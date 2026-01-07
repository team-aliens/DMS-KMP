package resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import resetpassword.EMAIL_VERIFICATION_CODE_LENGTH
import team.aliens.dms.kmp.core.common.timer.CountDownTimer
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsSymbol
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.textfield.DmsNumberField
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.timer.DmsTimer
import team.aliens.dms.kmp.core.model.message.TextFieldError
import team.aliens.dms.kmp.core.model.message.isError

@Composable
internal fun EmailVerificationContent(
    modifier: Modifier = Modifier,
    countDownTimer: CountDownTimer,
    email: String,
    emailVerificationCode: String,
    isResendLoading: Boolean,
    textFieldError: TextFieldError,
    onEmailVerificationCodeChange: (String) -> Unit,
    onResendCode: () -> Unit,
    onTimerFinished: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        Column{
            DmsSymbol()
            DmsText(
                modifier = Modifier.topPadding(20.dp),
                text = "이메일 인증",
                style = DmsTypography.TitleB,
                color = DmsTheme.colors.onTertiaryContainer,
            )
            Column (
                modifier = Modifier.topPadding(12.dp),
            ) {
                DmsText(
                    text = "$email 이메일로 전송된",
                    style = DmsTypography.BodyM,
                    color = DmsTheme.colors.inverseSurface,
                )
                Row {
                    DmsText(
                        text = "인증번호 ${EMAIL_VERIFICATION_CODE_LENGTH}자리를 ",
                        style = DmsTypography.BodyM,
                        color = DmsTheme.colors.inverseSurface,
                    )
                    DmsTimer(
                        countdownTimer = countDownTimer,
                        onTimerFinished = onTimerFinished,
                    )
                    DmsText(
                        text = " 내로 입력해주세요.",
                        style = DmsTypography.BodyM,
                        color = DmsTheme.colors.inverseSurface,
                    )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            DmsNumberField(
                totalLength = EMAIL_VERIFICATION_CODE_LENGTH,
                value = emailVerificationCode,
                onValueChange = onEmailVerificationCodeChange,
                isError = textFieldError.isError(),
                errorMessage = textFieldError.message,
            )
            DmsButton(
                text = "인증코드 재발송",
                buttonType = ButtonType.Underline,
                buttonColor = ButtonColor.Gray,
                onClick = onResendCode,
                isLoading = isResendLoading,
            )
        }
    }
}
