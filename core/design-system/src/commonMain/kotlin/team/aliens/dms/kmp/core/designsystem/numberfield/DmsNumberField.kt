package team.aliens.dms.kmp.core.designsystem.numberfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

@Composable
fun DmsNumberField(
    modifier: Modifier = Modifier,
    totalLength: Int,
    value: String,
    onValueChange: (String) -> Unit,
    spaceSize: Dp = 12.dp,
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = value.take(totalLength),
        enabled = enabled,
        onValueChange = { newValue ->
            if (newValue.length <= totalLength) {
                onValueChange(newValue)
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next,
        ),
    ) { _ ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spaceSize),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(totalLength) { index ->
                    val borderColor = if (isError) {
                        DmsTheme.colors.onError
                    } else if (value.length > index) {
                        DmsTheme.colors.primaryContainer
                    } else {
                        DmsTheme.colors.background
                    }
                    val text = if (index <= value.length - 1) {
                        value.getOrNull(index)
                            .toString()
                    } else {
                        ""
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.2.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .background(color = DmsTheme.colors.background)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DmsText(
                            text = text,
                            style = DmsTypography.TitleB,
                            color = DmsTheme.colors.onTertiaryContainer,
                        )
                    }
                }
            }
            if (isError) {
                errorMessage?.let {
                    DmsText(
                        text = it,
                        style = DmsTypography.Button2,
                        color = DmsTheme.colors.outline,
                    )
                }
            }
        }
    }
}
