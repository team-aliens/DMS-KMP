package team.aliens.dms.kmp.feature.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                DmsText(
                    text = "$gcn $name",
                    style = DmsTypography.Title1,
                    color = DmsTheme.colors.surfaceBright,
                )
                GenderTag(genderType = genderType)
            }
            DmsText(
                text = schoolName,
                style = DmsTypography.Body3,
                color = DmsTheme.colors.onTertiaryContainer,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        ProfileImage(profileImageUrl = profileImageUrl)
    }
}

@Composable
private fun GenderTag(
    modifier: Modifier = Modifier,
    genderType: GenderType,
) {
    val (text, textColor, backgroundColor) = when (genderType) {
        GenderType.MALE -> Triple("남", DmsTheme.colors.inversePrimary, DmsTheme.colors.primary)
        GenderType.FEMALE -> Triple("여", DmsTheme.colors.outline, DmsTheme.colors.error)
        GenderType.ALL -> Triple("기타", DmsTheme.colors.onBackground, DmsTheme.colors.inverseSurface)
    }

    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = RoundedCornerShape(24.dp),
        ).padding(
            horizontal = 24.dp,
            vertical = 4.dp,
        ),
        contentAlignment = Alignment.Center,
    ) {
        DmsText(
            text = text,
            style = DmsTypography.Caption,
            color = textColor,
        )
    }
}

@Composable
private fun ProfileImage(
    modifier: Modifier = Modifier,
    profileImageUrl: String?,
) {
    Box(
        modifier = modifier.size(74.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        AsyncImage(
            modifier = Modifier.clip(CircleShape),
            model = ImageRequest.Builder(context = LocalPlatformContext.current)
                .data(profileImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        DmsIconButton(
            modifier = Modifier
                .background(
                    color = DmsTheme.colors.surface,
                    shape = CircleShape,
                ),
            resource = DmsIcon.Edit,
            tint = DmsTheme.colors.inverseSurface,
            onClick = {},
        )
    }
}
