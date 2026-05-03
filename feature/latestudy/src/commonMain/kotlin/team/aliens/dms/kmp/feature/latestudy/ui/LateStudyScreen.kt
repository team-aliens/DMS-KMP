package team.aliens.dms.kmp.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.latestudy.component.CalendarYearMonth
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyCalendarSection
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyReasonSection
import team.aliens.dms.kmp.feature.latestudy.component.LateStudySectionCard
import team.aliens.dms.kmp.feature.latestudy.component.LateStudyTypeItem
import team.aliens.dms.kmp.feature.latestudy.ui.component.LateStudyTeacherSection

data class TeacherUiModel(
    val teacherId: String,
    val teacherName: String,
)

@Composable
fun LateStudyScreen(
    onBack: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    var selectedType by remember { mutableStateOf<String?>(null) }

    val types = listOf(
        "개인 공부",
        "개인 프로젝트",
        "팀 프로젝트",
        "대회 프로젝트",
        "기타",
    )

    var currentMonth by remember {
        mutableStateOf(
            CalendarYearMonth(
                year = 2026,
                monthNumber = 4,
            ),
        )
    }

    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    var teacherKeyword by remember { mutableStateOf("") }
    var selectedTeacherId by remember { mutableStateOf<String?>(null) }
    var selectedTeacherName by remember { mutableStateOf<String?>(null) }

    val teachers = remember {
        mutableStateListOf(
            TeacherUiModel("1", "김선생"),
            TeacherUiModel("2", "이선생"),
            TeacherUiModel("3", "박선생"),
        )
    }

    val filteredTeachers = if (teacherKeyword.isBlank()) {
        emptyList()
    } else {
        teachers.filter { teacher ->
            teacher.teacherName.contains(teacherKeyword)
        }
    }

    var reason by remember { mutableStateOf("") }

    val isEnabled = selectedTeacherId != null &&
            selectedType != null &&
            startDate != null &&
            reason.isNotBlank()

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
            color = DmsTheme.colors.scrim,
            modifier = Modifier
                .size(80.dp, 24.dp)
                .padding(start = 4.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "새벽 자습 신청",
            color = DmsTheme.colors.scrim,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LateStudyTeacherSection(
            value = teacherKeyword,
            onValueChange = {
                teacherKeyword = it
                selectedTeacherId = null
                selectedTeacherName = null
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        LateStudySectionCard {
            Text(
                text = "유형",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = DmsTheme.colors.scrim,
            )

            Spacer(modifier = Modifier.height(8.dp))

            types.forEach { type ->
                LateStudyTypeItem(
                    text = type,
                    selected = selectedType == type,
                    onClick = { selectedType = type },
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyCalendarSection(
            currentMonth = currentMonth,
            startDate = startDate,
            endDate = endDate,
            onPrevMonthClick = {
                currentMonth = currentMonth.minusMonths(1)
            },
            onNextMonthClick = {
                currentMonth = currentMonth.plusMonths(1)
            },
            onDateClick = { clickedDate ->
                when {
                    startDate == null -> {
                        startDate = clickedDate
                    }

                    endDate == null && startDate != null && clickedDate >= startDate!! -> {
                        endDate = clickedDate
                    }

                    else -> {
                        startDate = clickedDate
                        endDate = null
                    }
                }
            },
        )

        Spacer(modifier = Modifier.height(20.dp))

        LateStudyReasonSection(
            value = reason,
            onValueChange = { reason = it },
        )

        Spacer(modifier = Modifier.height(20.dp))

        DmsButton(
            modifier = Modifier.fillMaxWidth(),
            text = "신청하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            enabled = isEnabled,
            onClick = {
                if (isEnabled) {
                    onShowSnackBar(
                        DmsSnackBarType.SUCCESS,
                        "새벽 자습 신청이 완료되었습니다",
                    )
                } else {
                    onShowSnackBar(
                        DmsSnackBarType.ERROR,
                        "모두 선택해주세요",
                    )
                }
            },
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
