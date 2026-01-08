package resetpassword

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import resetpassword.model.ResetPasswordTextFieldError
import team.aliens.dms.kmp.core.common.base.BaseViewModel
import team.aliens.dms.kmp.core.common.exception.network.NotFoundException
import team.aliens.dms.kmp.core.common.exception.network.TooManyRequestsException
import team.aliens.dms.kmp.core.common.util.Regex
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.CheckIdExistsUseCase
import team.aliens.dms.kmp.core.domain.usecase.auth.SendEmailVerificationCodeUseCase
import team.aliens.dms.kmp.core.domain.usecase.student.ResetPasswordUseCase
import team.aliens.dms.kmp.core.model.message.TextFieldError
import team.aliens.dms.kmp.core.model.type.EmailVerificationType

internal class ResetPasswordViewModel(
    private val checkIdExistsUseCase: CheckIdExistsUseCase,
    private val sendEmailVerificationCodeUseCase: SendEmailVerificationCodeUseCase,
    private val checkEmailVerificationCodeUseCase: CheckEmailVerificationCodeUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : BaseViewModel<ResetPasswordState, ResetPasswordSideEffect>(ResetPasswordState()) {

    private fun checkIdExists() = viewModelScope.launch {
        setState { state.value.copy(isLoading = true) }
        checkIdExistsUseCase(accountId = state.value.accountId)
            .onSuccess {
                setState { state.value.copy(hashEmail = it.email) }
                postSideEffect(ResetPasswordSideEffect.MoveToNext)
            }.onFailure { exception ->
                when (exception) {
                    is NotFoundException -> postSideEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                    else -> postSideEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
                }
            }
        setState { state.value.copy(isLoading = false) }
    }

    private fun sendEmailVerificationCode() = viewModelScope.launch {
        setState {
            state.value.copy(
                isLoading = true,
                emailVerificationCodeTextFieldError = TextFieldError.None(),
            )
        }
        sendEmailVerificationCodeUseCase(
            email = state.value.email,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            postSideEffect(ResetPasswordSideEffect.MoveToNext)
            postSideEffect(ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar)
        }.onFailure { exception ->
            when (exception) {
                is NotFoundException -> postSideEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                is TooManyRequestsException -> postSideEffect(ResetPasswordSideEffect.ShowTooManyRequestSnackBar)
                else -> postSideEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
            }
        }
        setState { state.value.copy(isLoading = false) }
    }

    internal fun resendEmailVerificationCode() = viewModelScope.launch {
        setState {
            state.value.copy(
                isResendLoading = true,
                emailVerificationCodeTextFieldError = TextFieldError.None(),
            )
        }
        sendEmailVerificationCodeUseCase(
            email = state.value.email,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            setState { state.value.copy(emailVerificationCodeTextFieldError = TextFieldError.None()) }
            postSideEffect(ResetPasswordSideEffect.ResetCountDownTimer)
            postSideEffect(ResetPasswordSideEffect.ShowSendEmailSuccessSnackBar)
        }.onFailure { exception ->
            when (exception) {
                is NotFoundException -> postSideEffect(ResetPasswordSideEffect.ShowNotFoundAccountIdSnackBar)
                else -> postSideEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
            }
        }
        setState { state.value.copy(isResendLoading = false) }
    }

    private fun checkEmailVerificationCode() = viewModelScope.launch {
        setState {
            state.value.copy(
                isLoading = true,
                emailVerificationCodeTextFieldError = TextFieldError.None(),
            )
        }
        checkEmailVerificationCodeUseCase(
            email = state.value.email,
            code = state.value.emailVerificationCode,
            type = EmailVerificationType.PASSWORD,
        ).onSuccess {
            postSideEffect(ResetPasswordSideEffect.MoveToNext)
        }.onFailure {
            setState { state.value.copy(emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.InvalidEmailVerificationCode()) }
            postSideEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
        }
        setState { state.value.copy(isLoading = false) }
    }

    private fun resetPassword() = viewModelScope.launch {
        resetPasswordUseCase(
            accountId = state.value.accountId,
            name = state.value.name,
            email = state.value.email,
            emailVerificationCode = state.value.emailVerificationCode,
            newPassword = state.value.password,
        ).onSuccess {
            postSideEffect(ResetPasswordSideEffect.ShowPasswordResetSuccessSnackBar)
            postSideEffect(ResetPasswordSideEffect.NavigateUp)
        }.onFailure {
            postSideEffect(ResetPasswordSideEffect.ShowServerErrorSnackBar)
        }
    }

    internal fun moveNext(step: ResetPasswordStep?) = viewModelScope.launch {
        if (step == null) return@launch
        when (step) {
            ResetPasswordStep.InputId -> checkIdExists()
            ResetPasswordStep.InputUserInfo -> sendEmailVerificationCode()
            ResetPasswordStep.InputEmailVerificationCode -> checkEmailVerificationCode()
            ResetPasswordStep.InputNewPassword -> resetPassword()
        }
    }

    internal fun changePage(step: ResetPasswordStep?) = viewModelScope.launch {
        if (step == null) return@launch
        when (step) {
            ResetPasswordStep.InputId -> {
                val accountId = state.value.accountId.isNotEmpty()
                setState { state.value.copy(buttonEnabled = accountId) }
            }

            ResetPasswordStep.InputUserInfo -> {
                val name = state.value.name.isNotEmpty()
                val email = state.value.email.isNotEmpty()
                setState { state.value.copy(buttonEnabled = name && email) }
            }

            ResetPasswordStep.InputEmailVerificationCode -> {
                val emailVerificationCode = state.value.emailVerificationCode.isNotEmpty()
                setState { state.value.copy(buttonEnabled = emailVerificationCode) }
            }

            ResetPasswordStep.InputNewPassword -> {
                val password = state.value.password.isNotEmpty()
                val confirmPassword = state.value.confirmPassword == state.value.password
                setState { state.value.copy(buttonEnabled = password && confirmPassword) }
            }
        }
    }

    internal fun setEmailVerificationTimerFinished(isFinished: Boolean) {
        setState { state.value.copy(isEmailVerificationTimerFinished = isFinished) }
        if (isFinished) {
            setState {
                state.value.copy(
                    emailVerificationCodeTextFieldError = ResetPasswordTextFieldError.EmailVerificationCodeTimeExpired(),
                    buttonEnabled = false,
                )
            }
        }
    }

    internal fun setAccountId(accountId: String) {
        setState { state.value.copy(accountId = accountId, buttonEnabled = accountId.isNotEmpty()) }
    }

    internal fun setName(name: String) {
        val buttonEnabled = name.isNotEmpty() && state.value.email.isNotEmpty()
        setState { state.value.copy(name = name, buttonEnabled = buttonEnabled) }
    }

    internal fun setEmail(email: String) {
        val buttonEnabled = email.isNotEmpty() && state.value.name.isNotEmpty()
        setState { state.value.copy(email = email, buttonEnabled = buttonEnabled) }
    }

    internal fun setEmailVerificationCode(emailVerificationCode: String) {
        val buttonEnabled =
            emailVerificationCode.length == EMAIL_VERIFICATION_CODE_LENGTH && !state.value.isEmailVerificationTimerFinished
        setState {
            state.value.copy(
                emailVerificationCode = emailVerificationCode,
                buttonEnabled = buttonEnabled,
            )
        }
    }

    internal fun setPassword(password: String) {
        setState { state.value.copy(password = password) }
        passwordValidation()
    }

    internal fun setConfirmPassword(confirmPassword: String) {
        setState { state.value.copy(confirmPassword = confirmPassword) }
        passwordValidation()
    }

    private fun passwordValidation() {
        val password = state.value.password
        val confirmPassword = state.value.confirmPassword
        val isValidPassword = Regex(Regex.PASSWORD).matches(password)
        val isMatchPassword = password == confirmPassword
        val buttonEnabled =
            isValidPassword && isMatchPassword && password.isNotEmpty() && confirmPassword.isNotEmpty()
        val passwordTextFieldError = if (isValidPassword) {
            TextFieldError.None()
        } else {
            ResetPasswordTextFieldError.InvalidPasswordFormat()
        }
        val confirmPasswordTextFieldError = if (isMatchPassword || confirmPassword.isEmpty()) {
            TextFieldError.None()
        } else {
            ResetPasswordTextFieldError.PasswordMismatch()
        }
        setState {
            state.value.copy(
                passwordTextFieldError = passwordTextFieldError,
                confirmPasswordTextFieldError = confirmPasswordTextFieldError,
                buttonEnabled = buttonEnabled,
            )
        }
    }
}

internal data class ResetPasswordState(
    val accountId: String = "",
    val name: String = "",
    val hashEmail: String = "",
    val email: String = "",
    val emailVerificationCode: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val buttonEnabled: Boolean = false,
    val isResendLoading: Boolean = false,
    val isEmailVerificationTimerFinished: Boolean = false,
    val emailVerificationCodeTextFieldError: TextFieldError = TextFieldError.None(),
    val passwordTextFieldError: TextFieldError = TextFieldError.None(),
    val confirmPasswordTextFieldError: TextFieldError = TextFieldError.None(),
)

internal sealed class ResetPasswordSideEffect {
    data object NavigateUp : ResetPasswordSideEffect()
    data object ResetCountDownTimer : ResetPasswordSideEffect()
    data object MoveToNext : ResetPasswordSideEffect()
    data object ShowSendEmailSuccessSnackBar : ResetPasswordSideEffect()
    data object ShowNotFoundAccountIdSnackBar : ResetPasswordSideEffect()
    data object ShowTooManyRequestSnackBar : ResetPasswordSideEffect()
    data object ShowServerErrorSnackBar : ResetPasswordSideEffect()
    data object ShowPasswordResetSuccessSnackBar : ResetPasswordSideEffect()
}
