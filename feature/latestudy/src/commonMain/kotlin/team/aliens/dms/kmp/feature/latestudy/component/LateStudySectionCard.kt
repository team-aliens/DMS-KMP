package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme

@Composable
fun LateStudySectionCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = DmsTheme.colors.background,
                shape = RoundedCornerShape(16.dp),
            )
            .padding(vertical = 12.dp),
    ) {
        content()
    }
}
