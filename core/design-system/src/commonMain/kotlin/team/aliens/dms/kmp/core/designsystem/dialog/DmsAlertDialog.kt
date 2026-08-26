package team.aliens.dms.kmp.core.designsystem.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsAlertDialog(
    title: String,
    description: String? = null,
    buttonText: String = "확인",
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClose,
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors =
                CardDefaults.cardColors(
                    containerColor = DmsTheme.colors.surface,
                    contentColor = DmsTheme.colors.surfaceContainer,
                ),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                DmsText(
                    text = title,
                    style = DmsTypography.LBodyB,
                    color = DmsTheme.colors.surfaceContainer,
                )
                description?.let {
                    DmsText(
                        text = it,
                        style = DmsTypography.labelB,
                        color = DmsTheme.colors.inverseSurface,
                    )
                }
                DmsButton(
                    modifier = Modifier.align(Alignment.End),
                    text = buttonText,
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = onClose,
                )
            }
        }
    }
}
