package team.aliens.dms.kmp.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import team.aliens.dms.kmp.core.common.ui.startPadding
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.model.type.GenderType

@Composable
internal fun ProfileContent(
    modifier: Modifier = Modifier,
    gcn: String,
    name: String,
    schoolName: String,
    genderType: GenderType,
    profileImageUrl: String?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = DmsTheme.colors.surfaceTint, shape = RoundedCornerShape(32.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(context = LocalPlatformContext.current)
                .data(profileImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.startPadding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                DmsText(
                    text = "$gcn $name",
                    style = DmsTypography.BodyB,
                    color = DmsTheme.colors.surfaceContainer,
                )
                GenderTag(genderType = genderType)
            }
            DmsText(
                text = schoolName,
                style = DmsTypography.labelM,
                color = DmsTheme.colors.inverseOnSurface,
            )
        }
    }
}

@Composable
private fun GenderTag(
    modifier: Modifier = Modifier,
    genderType: GenderType,
) {
    val (text, textColor, backgroundColor) = when (genderType) {
        GenderType.MALE -> Triple("남", DmsTheme.colors.onPrimaryContainer, DmsTheme.colors.primary)
        GenderType.FEMALE -> Triple("여", DmsTheme.colors.onErrorContainer, DmsTheme.colors.error)
        GenderType.ALL -> Triple("기타", DmsTheme.colors.tertiaryContainer, DmsTheme.colors.onSurface)
    }

    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(12.dp),
        ).padding(
            horizontal = 12.dp,
            vertical = 8.dp,
        ),
        contentAlignment = Alignment.Center,
    ) {
        DmsText(
            text = text,
            style = DmsTypography.labelB,
            color = textColor,
        )
    }
}
