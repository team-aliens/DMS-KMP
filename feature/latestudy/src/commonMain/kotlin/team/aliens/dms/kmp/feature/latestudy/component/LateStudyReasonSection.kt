package team.aliens.dms.kmp.feature.latestudy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography

private const val REASON_MAX_LENGTH = 200

@Composable
fun LateStudyReasonSection(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LateStudySectionCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "사유",
                color = DmsTheme.colors.inverseOnSurface,
                style = DmsTypography.BodyB,
            )

            Text(
                text = "${value.length}/$REASON_MAX_LENGTH",
                color = DmsTheme.colors.inverseSurface,
                modifier = Modifier,
                style = DmsTypography.SLabelM,
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 24.dp)
                .background(
                    color = DmsTheme.colors.onSurface,
                    shape = RoundedCornerShape(32.dp),
                )
                .height(220.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            BasicTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.length <= REASON_MAX_LENGTH) {
                        onValueChange(newValue)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = DmsTypography.BodyM.copy(
                    color = DmsTheme.colors.tertiaryContainer,
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            Text(
                                text = "새벽 자습을 신청한 이유를 작성해주세요",
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
