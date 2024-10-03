package team.aliens.dms.kmp.core.designsystem.numberfield

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    enabled: Boolean = true,
    isError: Boolean = false,
    errorDescription: String? = null,
) {
    BasicTextField(
        modifier = modifier,
        value = value.take(totalLength),
        enabled = enabled,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Next,
        ),
    ) { _ ->
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items(totalLength) { index ->
                    val borderColor = if (isError) {
                        DmsTheme.colors.outlineVariant
                    } else if (value.length - 1 == index) {
                        DmsTheme.colors.inversePrimary
                    } else if (value.length > index) {
                        DmsTheme.colors.onSecondary
                    } else {
                        DmsTheme.colors.surface
                    }
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = RoundedCornerShape(4.dp),
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        DmsText(
                            text = if (index <= value.length - 1) value[index].toString() else "",
                            style = DmsTypography.Body1Medium,
                        )
                    }
                }
            }
            errorDescription?.let {
                DmsText(
                    text = errorDescription,
                    style = DmsTypography.Body1Medium,
                    color = DmsTheme.colors.outlineVariant,
                )
            }
        }
    }
}
