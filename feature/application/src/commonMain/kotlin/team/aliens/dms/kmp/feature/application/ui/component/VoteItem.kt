package team.aliens.dms.kmp.feature.application.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun VoteItem(
    modifier: Modifier = Modifier,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DmsText(
            text = "${startTime.toFormat()} ~ ${endTime.toFormat()}",
            style = DmsTypography.Button1,
            color = DmsTheme.colors.inversePrimary,
        )
        ApplicationCard(
            title = title,
            appliedTitle = null,
            description = description,
            buttonText = "투표하기",
            onButtonClick = onClick,
        )
    }
}

private fun LocalDateTime.toFormat(): String {
    return "${this.monthNumber}/${this.dayOfMonth} ${this.time}"
}
