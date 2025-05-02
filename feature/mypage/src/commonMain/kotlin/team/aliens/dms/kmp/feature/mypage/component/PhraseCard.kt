package team.aliens.dms.kmp.feature.mypage.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun PhraseCard(
    modifier: Modifier = Modifier,
    phrase: String,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = DmsTheme.colors.primary,
        ),
    ) {
        DmsText(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            text = phrase,
            style = DmsTypography.Body3,
            color = DmsTheme.colors.surfaceBright,
        )
    }
}
