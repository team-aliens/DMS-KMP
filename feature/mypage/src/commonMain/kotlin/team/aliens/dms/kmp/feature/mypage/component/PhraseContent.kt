package team.aliens.dms.kmp.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun PhraseContent(
    modifier: Modifier = Modifier,
    phrase: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp, vertical = 26.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsText(
            text = phrase,
            style = DmsTypography.BodyB,
            color = DmsTheme.colors.inverseSurface,
        )
    }
}
