package team.aliens.dms.kmp.core.designsystem.appbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsTopAppBar(
    modifier: Modifier = Modifier,
    showLogo: Boolean = false,
    onBackPressed: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
    title: String? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(DmsTheme.colors.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = if (showLogo || onBackPressed != null) Arrangement.SpaceBetween else Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showLogo) {
                Image(
                    painter = painterResource(
                        if (isSystemInDarkTheme()) {
                            DmsIcon.SymbolDark
                        } else {
                            DmsIcon.SymbolLight
                        },
                    ),
                    contentDescription = null,
                )
            }
            onBackPressed?.let {
                DmsIconButton(
                    resource = DmsIcon.ArrowBack,
                    tint = DmsTheme.colors.surfaceContainerLow,
                    onClick = it,
                )
            }
            actions?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    content = it,
                )
            }
        }
        title?.let {
            DmsText(
                text = it,
                style = DmsTypography.SubtitleSemiBold,
            )
        }
    }
}

@Composable
fun DmsLargeTopAppBar(
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null,
    title: String,
    description: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DmsTheme.colors.background),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            onBackPressed?.let {
                DmsIconButton(
                    resource = DmsIcon.ArrowBack,
                    tint = DmsTheme.colors.surfaceContainerLow,
                    onClick = it,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            DmsText(
                text = title,
                style = DmsTypography.Title1SemiBold,
            )
            description?.let {
                DmsText(
                    text = description,
                    style = DmsTypography.Body1Medium,
                    color = DmsTheme.colors.onSurface,
                )
            }
        }
    }
}
