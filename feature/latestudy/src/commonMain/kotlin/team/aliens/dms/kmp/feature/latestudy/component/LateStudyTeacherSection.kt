package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography

@Composable
fun LateStudyTeacherSection(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "담당 선생님",
                color = DmsTheme.colors.inverseOnSurface,
                style = DmsTypography.BodyB,
            )

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = DmsTypography.BodyM.copy(
                    color = DmsTheme.colors.tertiaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = DmsTheme.colors.onSurface,
                                shape = RoundedCornerShape(32.dp),
                            )
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                    ) {
                        if (value.isEmpty()) {
                            Text(
                                text = "홍길동",
                                color = DmsTheme.colors.inverseSurface,
                                style = DmsTypography.BodyM,
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }
    }
}
