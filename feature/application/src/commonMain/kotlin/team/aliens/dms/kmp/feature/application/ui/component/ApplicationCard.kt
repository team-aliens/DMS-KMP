package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.modifier.DmsShadowType
import team.aliens.dms.kmp.core.designsystem.modifier.dmsShadowModifier
import team.aliens.dms.kmp.core.designsystem.tag.DmsTag
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun ApplicationCard(
    modifier: Modifier = Modifier,
    title: String,
    appliedTitle: String? = null,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .dmsShadowModifier(
                dmsShadowType = DmsShadowType.Light20,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = DmsTheme.colors.background,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DmsText(
                    text = title,
                    style = DmsTypography.Header3,
                    color = DmsTheme.colors.onBackground,
                )
                Spacer(modifier = Modifier.weight(1f))
                appliedTitle?.let { text ->
                    DmsTag(text = text)
                }
            }
            DmsText(
                text = description,
                style = DmsTypography.Body3,
                color = DmsTheme.colors.tertiaryContainer,
            )
        }
        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = buttonText,
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            onClick = onButtonClick,
        )
    }
}
