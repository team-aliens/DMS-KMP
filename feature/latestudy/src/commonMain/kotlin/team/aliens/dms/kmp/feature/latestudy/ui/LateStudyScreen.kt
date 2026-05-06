package team.aliens.dms.kmp.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
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
    val today = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

    var currentMonth by remember {
        mutableStateOf(
            CalendarYearMonth(
                year = today.year,
                monthNumber = today.monthNumber,
            ),
        )
    }

    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

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
            color = DmsTheme.colors.scrim,
            modifier = Modifier
                .size(80.dp, 24.dp)
                .padding(start = 4.dp)
                .clickable { onBack() },
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "새벽 자습 신청",
            color = DmsTheme.colors.scrim,
        )

        Spacer(modifier = Modifier.height(16.dp))

        LateStudyTeacherSection(
            value = viewModel.teacherKeyword,
            onValueChange = viewModel::updateTeacherKeyword,
        )

        Spacer(modifier = Modifier.height(20.dp))

        LateStudySectionCard {
            Text(
                text = "유형",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                color = DmsTheme.colors.scrim,
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

                    endDate == null && clickedDate >= startDate!! -> {
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
                val selectedStartDate = startDate

                if (selectedStartDate == null) {
                    onShowSnackBar(DmsSnackBarType.ERROR, "모두 선택해주세요")
                    return@DmsButton
                }

                viewModel.submitLateStudy(
                    startDate = selectedStartDate.toString(),
                    endDate = (endDate ?: selectedStartDate).toString(),
                    onSuccess = {
                        onShowSnackBar(
                            DmsSnackBarType.SUCCESS,
                            "새벽 자습 신청이 완료되었습니다",
                        )
                    },
                    onFailure = {
                        onShowSnackBar(
                            DmsSnackBarType.ERROR,
                            "모두 선택해주세요",
                        )
                    },
                )
            },
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}
