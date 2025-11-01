package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.util.toDateString

@Composable
internal fun TitleContent(
    modifier: Modifier = Modifier,
    title: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        DmsText(
            text = title,
            style = DmsTypography.LBodyB,
            color = DmsTheme.colors.tertiaryContainer,
        )
        DmsText(
            text = "${startTime.toDateString()} ~ ${endTime.toDateString()}",
            style = DmsTypography.labelM,
            color = DmsTheme.colors.inverseOnSurface,
        )
    }
}
