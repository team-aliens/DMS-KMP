package team.aliens.dms.kmp.feature.signup.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
internal fun SignUpInfoBanner(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        DmsText(
            text = title,
            style = DmsTypography.TitleB,
            color = DmsTheme.colors.onTertiaryContainer,
        )
        DmsText(
            text = description,
            style = DmsTypography.BodyM,
            color = DmsTheme.colors.inverseSurface,
        )
    }
}
