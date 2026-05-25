package team.aliens.dms.kmp.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.latestudy.TeacherModel
import team.aliens.dms.kmp.feature.latestudy.component.CalendarYearMonth
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyCalendarSection
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyReasonSection
import team.aliens.dms.kmp.feature.latestudy.component.LateStudySectionCard
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyTeacherSection
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyTypeItem
import team.aliens.dms.kmp.feature.latestudy.viewmodel.LateStudyViewModel

@OptIn(ExperimentalTime::class)
@Composable
fun LateStudyScreen(
    onBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    viewModel: LateStudyViewModel = koinViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    var currentMonth by remember {
        mutableStateOf(CalendarYearMonth(today.year, today.monthNumber))
    }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }
    var isDropdownVisible by remember { mutableStateOf(false) }

    val filteredTeachers = remember(viewModel.teacherKeyword, viewModel.teachers) {
        if (viewModel.teacherKeyword.isBlank()) emptyList()
        else viewModel.teachers.filter { it.name.contains(viewModel.teacherKeyword) }
    }

    val isEnabled = viewModel.selectedTeacherId != null &&
            viewModel.selectedTypeId != null &&
            startDate != null &&
            viewModel.reason.isNotBlank() &&
            !viewModel.isSubmitting

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 16.dp),
    ) {
        Text(
            text = "< 뒤로가기",
            color = DmsTheme.colors.onBackground,
            style = DmsTypography.BodyM,
            modifier = Modifier
                .padding(start = 4.dp)
                .clickable { onBack() },
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "새벽 자습 신청",
            color = DmsTheme.colors.onBackground,
            style = DmsTypography.TitleB,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TeacherSearchSection(
            teacherKeyword = viewModel.teacherKeyword,
            onKeywordChange = {
                viewModel.updateTeacherKeyword(it)
                isDropdownVisible = true
            },
            filteredTeachers = filteredTeachers,
            isDropdownVisible = isDropdownVisible,
            onTeacherClick = {
                viewModel.selectTeacher(it)
                isDropdownVisible = false
                focusManager.clearFocus()
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        LateStudySectionCard {
            Text(
                text = "유형",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = DmsTheme.colors.onBackground,
                style = DmsTypography.BodyB,
            )

            Spacer(modifier = Modifier.height(8.dp))

            viewModel.studyTypes.forEach { type ->
                LateStudyTypeItem(
                    text = type.name,
                    selected = viewModel.selectedTypeId == type.id,
                    onClick = { viewModel.selectStudyType(type.id) },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyCalendarSection(
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onPrevMonthClick = { currentMonth = currentMonth.minusMonths(1) },
            onNextMonthClick = { currentMonth = currentMonth.plusMonths(1) },
            onDateClick = { clickedDate ->
                when {
                    startDate == null -> startDate = clickedDate
                    endDate == null && clickedDate >= startDate!! -> endDate = clickedDate
                    else -> {
                        startDate = clickedDate
                        endDate = null
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyReasonSection(
            value = viewModel.reason,
            onValueChange = viewModel::updateReason,
        )

        Spacer(modifier = Modifier.height(20.dp))

        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "신청하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled = isEnabled,
            onClick = {
                val selectedStartDate = startDate ?: return@DmsButton

                viewModel.submitLateStudy(
                    startDate = selectedStartDate.toString(),
                    endDate = (endDate ?: selectedStartDate).toString(),
                    onSuccess = {
                        onShowSnackBar(DmsSnackBarType.SUCCESS, "새벽 자습 신청이 완료되었습니다")
                        onBack()
                    },
                    onFailure = { message ->
                        onShowSnackBar(DmsSnackBarType.ERROR, message)
                    },
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun TeacherSearchSection(
    teacherKeyword: String,
    onKeywordChange: (String) -> Unit,
    filteredTeachers: List<TeacherModel>,
    isDropdownVisible: Boolean,
    onTeacherClick: (TeacherModel) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(10f),
    ) {
        Column {
            LateStudyTeacherSection(
                value = teacherKeyword,
                onValueChange = onKeywordChange,
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isDropdownVisible && filteredTeachers.isNotEmpty()) {
            TeacherDropdown(
                teachers = filteredTeachers,
                keyword = teacherKeyword,
                onTeacherClick = onTeacherClick,
            )
        }
    }
}

@Composable
private fun TeacherDropdown(
    teachers: List<TeacherModel>,
    keyword: String,
    onTeacherClick: (TeacherModel) -> Unit,
) {
    val highlightColor = DmsTheme.colors.primary

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .offset(y = 120.dp)
            .zIndex(10f),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(28.dp),
                    clip = false,
                    ambientColor = DmsTheme.colors.primary.copy(alpha = 0.25f),
                    spotColor = DmsTheme.colors.primary.copy(alpha = 0.25f),
                )
                .background(
                    color = DmsTheme.colors.surfaceTint,
                    shape = RoundedCornerShape(28.dp),
                )
                .heightIn(max = 220.dp)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 12.dp),
        ) {
            teachers.forEach { teacher ->
                TeacherDropdownItem(
                    teacher = teacher,
                    keyword = keyword,
                    highlightColor = highlightColor,
                    onClick = { onTeacherClick(teacher) },
                )
            }
        }
    }
}

@Composable
private fun TeacherDropdownItem(
    teacher: TeacherModel,
    keyword: String,
    highlightColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = highlightText(
                text = teacher.name,
                keyword = keyword,
                highlightColor = highlightColor,
            ),
            color = DmsTheme.colors.onBackground,
            style = DmsTypography.BodyM,
        )
    }
}

private fun highlightText(
    text: String,
    keyword: String,
    highlightColor: androidx.compose.ui.graphics.Color,
) = buildAnnotatedString {
    val startIndex = text.indexOf(keyword)

    if (startIndex >= 0 && keyword.isNotEmpty()) {
        append(text.substring(0, startIndex))
        withStyle(style = SpanStyle(color = highlightColor)) {
            append(text.substring(startIndex, startIndex + keyword.length))
        }
        append(text.substring(startIndex + keyword.length))
    } else {
        append(text)
    }
}
