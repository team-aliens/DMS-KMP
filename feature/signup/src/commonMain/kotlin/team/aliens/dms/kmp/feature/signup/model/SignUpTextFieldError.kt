package team.aliens.dms.kmp.feature.signup.model

import team.aliens.dms.kmp.core.model.message.TextFieldError

internal sealed interface SignUpTextFieldError : TextFieldError {
    data class InvalidEmailVerificationCode(
        override val message: String? = "인증코드가 일치하지 않아요.",
    ) : SignUpTextFieldError

    data class EmailVerificationCodeTimeExpired(
        override val message: String? = "인증코드 입력시간이 만료되었어요.",
    ) : SignUpTextFieldError
}
