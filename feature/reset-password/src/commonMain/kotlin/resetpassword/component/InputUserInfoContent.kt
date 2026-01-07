package resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField

@Composable
internal fun InputUserInfoContent(
    modifier: Modifier = Modifier,
    name: String,
    email: String,
    hashEmail: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        ResetPasswordInfoContent(
            title = "비밀번호 찾기",
            description = "이름과 이메일을 입력해주세요!",
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            DmsTextField(
                label = "이름",
                hint = "이름 입력",
                value = name,
                onValueChange = onNameChange,
                showClearIcon = true,
            )
            DmsTextField(
                label = "이메일",
                hint = hashEmail,
                value = email,
                onValueChange = onEmailChange,
                keyboardType = KeyboardType.Email,
                showClearIcon = true,
            )
        }
    }
}
