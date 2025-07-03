package team.aliens.dms.kmp.feature.vote.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.core.designsystem.util.clickable
import team.aliens.dms.kmp.core.model.student.StudentModel

@Composable
internal fun StudentContent(
    modifier: Modifier = Modifier,
    title: String,
    students: List<StudentModel>,
    selectItem: String,
    onSelect: (String) -> Unit,
) {
    var selectGrade by remember { mutableStateOf(0) }
    val grades = listOf("1학년", "2학년", "3학년")

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 20.dp,
                    bottom = 16.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DmsText(
                text = title,
                style = DmsTypography.Header3,
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(grades) { index, grade ->
                    val buttonType =
                        if (index + 1 == selectGrade) ButtonType.Contained else ButtonType.Outlined
                    DmsButton(
                        text = grade,
                        buttonType = buttonType,
                        buttonColor = ButtonColor.Primary,
                        onClick = { selectGrade = index + 1 },
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = DmsTheme.colors.surface,
        )
        LazyColumn {
            val filteredStudents =
                if (selectGrade == 0) {
                    students
                } else {
                    students.filter { it.gcn.startsWith("$selectGrade") }
                }
            items(filteredStudents) { student ->
                StudentItem(
                    profileImageUrl = student.profileImageUrl,
                    name = student.name,
                    gcn = student.gcn,
                    isSelected = selectItem == student.id,
                    onClick = { onSelect(student.id) },
                )
            }
        }
    }
}

@Composable
private fun StudentItem(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    name: String,
    gcn: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        DmsTheme.colors.onPrimary
    } else {
        DmsTheme.colors.background
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(
                horizontal = 24.dp,
                vertical = 14.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(context = LocalPlatformContext.current)
                .data(profileImageUrl)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.weight(1f))
        DmsText(
            text = "$gcn $name",
            style = DmsTypography.Body1,
        )
    }
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth(),
        color = DmsTheme.colors.surface,
    )
}
