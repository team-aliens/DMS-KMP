package team.aliens.dms.kmp.core.model.message

interface TextFieldError {
    val message: String?

    data class None(override val message: String? = null) : TextFieldError
}

fun TextFieldError.isError(): Boolean = this !is TextFieldError.None
