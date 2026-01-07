package resetpassword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.common.ui.horizontalPadding
import team.aliens.dms.kmp.core.common.ui.topPadding
import team.aliens.dms.kmp.core.designsystem.textfield.DmsTextField

@Composable
internal fun InputIdContent(
    modifier: Modifier = Modifier,
    accountId: String,
    onAccountIdChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .topPadding(4.dp)
            .horizontalPadding(24.dp),
        verticalArrangement = Arrangement.spacedBy(48.dp),
    ) {
        ResetPasswordInfoContent(
            title = "비밀번호 찾기",
            description = "아이디를 입력해주세요!",
        )
        DmsTextField(
            label = "아이디",
            hint = "아이디 입력",
            value = accountId,
            onValueChange = onAccountIdChange,
            showClearIcon = true,
        )
    }
}
