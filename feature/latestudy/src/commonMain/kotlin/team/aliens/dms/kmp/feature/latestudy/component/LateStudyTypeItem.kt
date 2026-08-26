package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography

@Composable
fun LateStudyTypeItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(
                color = if (selected) {
                    DmsTheme.colors.primary
                } else {
                    DmsTheme.colors.surface
                },
            )
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton,
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            color = if (selected) {
                DmsTheme.colors.inversePrimary
            } else {
                DmsTheme.colors.inverseOnSurface
            },
            style = DmsTypography.BodyM,
        )

        Box(
            modifier = Modifier
                .size(20.dp)
                .then(
                    if (selected) {
                        Modifier.background(
                            color = DmsTheme.colors.onPrimaryContainer,
                            shape = CircleShape,
                        )
                    } else {
                        Modifier.border(
                            width = 2.dp,
                            color = DmsTheme.colors.inverseSurface,
                            shape = CircleShape,
                        )
                    },
                ),
            contentAlignment = Alignment.Center,
        ) {
            if (selected) {
                Icon(
                    painter = painterResource(DmsIcon.Check),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
    }
}
