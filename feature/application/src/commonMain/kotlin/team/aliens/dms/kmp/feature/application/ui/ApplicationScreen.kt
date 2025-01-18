package team.aliens.dms.kmp.feature.application.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun Application() {
    ApplicationScreen()
}

@Composable
private fun ApplicationScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DmsTopAppBar(title = "신청")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 28.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ApplicationCard(
                title = "잔류",
                description = "주말 기숙사 잔류 여부를 확인하고, 잔류 신청을 통해서 잔류 또는 귀가를 신청해 보세요.",
                buttonText = "잔류 신청하기",
                onButtonClick = { },
            )
            ApplicationCard(
                title = "외출",
                appliedTitle = "금요 귀가",
                description = "기숙사 생활 중 밖으로 나갈 일이 있다면, 외출 신청을 통해서 외출해 보세요.",
                buttonText = "와출 신청하기",
                onButtonClick = { },
            )
        }
    }
}

@Composable
private fun ApplicationCard(
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
            .border(
                width = 1.dp,
                color = DmsTheme.colors.surface,
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
                    DmsText(
                        modifier = Modifier
                            .background(
                                color = DmsTheme.colors.primary,
                                shape = RoundedCornerShape(24.dp),
                            )
                            .padding(
                                horizontal = 16.dp,
                                vertical = 6.dp,
                            ),
                        text = text,
                        style = DmsTypography.Caption,
                        color = DmsTheme.colors.inversePrimary,
                    )
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
