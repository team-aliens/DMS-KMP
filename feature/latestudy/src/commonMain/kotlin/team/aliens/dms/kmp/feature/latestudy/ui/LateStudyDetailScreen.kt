package team.aliens.dms.kmp.feature.latestudy.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.latestudy.StudyApplicationStatusModel
import team.aliens.dms.kmp.feature.latestudy.component.CalendarYearMonth
import team.aliens.dms.kmp.feature.latestudy.component.LateStudySectionCard
import team.aliens.dms.kmp.feature.latestudy.viewmodel.LateStudyDetailViewModel
import kotlin.time.ExperimentalTime

@Composable
fun LateStudyDetailScreen(
    onBack: () -> Unit,
    onCancel: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
    viewModel: LateStudyDetailViewModel = koinViewModel(),
) {
    var showCancelDialog by remember { mutableStateOf(false) }
    val application = viewModel.application

    if (showCancelDialog) {
        AlertDialog(
            title = { Text(text = "신청 취소하시겠습니까?", style = DmsTypography.BodyB) },
            onDismissRequest = { showCancelDialog = false },
            confirmButton = {
                DmsButton(
                    text = "확인",
                    buttonType = ButtonType.Contained,
                    buttonColor = ButtonColor.Error,
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        viewModel.cancelApplication(
                            onSuccess = {
                                showCancelDialog = false
                                onShowSnackBar(DmsSnackBarType.SUCCESS, "새벽 자습 신청을 취소했습니다")
                                onCancel()
                            },
                            onFailure = { message ->
                                showCancelDialog = false
                                onShowSnackBar(DmsSnackBarType.ERROR, message)
                            },
                        )
                    },
                )
            },
            dismissButton = {
                DmsButton(
                    text = "취소",
                    buttonType = ButtonType.Contained,
                    buttonColor = ButtonColor.Gray,
                    shape = RoundedCornerShape(16.dp),
                    onClick = { showCancelDialog = false },
                )
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
        ) {
            DmsIconButton(
                modifier = Modifier.padding(start = 2.dp),
                resource = DmsIcon.Backward,
                tint = DmsTheme.colors.scrim,
                size = 48.dp,
                contentPaddingValues = androidx.compose.foundation.layout.PaddingValues(12.dp),
                contentDescription = "뒤로가기",
                onClick = onBack,
            )

            Text(
                text = "새벽 자습 신청 상세",
                color = DmsTheme.colors.tertiaryContainer,
                style = DmsTypography.LBodyB,
                modifier = Modifier.padding(start = 14.dp, top = 8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                viewModel.isLoading ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(360.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = DmsTheme.colors.primary)
                    }

                application == null ->
                    Text(
                        text = "확인할 수 있는 새벽 자습 신청 내역이 없습니다.",
                        color = DmsTheme.colors.inverseSurface,
                        style = DmsTypography.BodyM,
                        modifier = Modifier.padding(16.dp),
                    )

                else -> LateStudyDetailContent(application)
            }

            Spacer(modifier = Modifier.height(132.dp))
        }

        if (application != null) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        color = DmsTheme.colors.surfaceTint,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                    )
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
            ) {
                DmsButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "신청 취소하기",
                    buttonType = ButtonType.Contained,
                    buttonColor = ButtonColor.Error,
                    enabled = application.status == "PENDING" && !viewModel.isCancelling,
                    isLoading = viewModel.isCancelling,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 20.dp),
                    onClick = { showCancelDialog = true },
                )
            }
        }
    }
}

@Composable
private fun LateStudyDetailContent(application: StudyApplicationStatusModel) {
    LateStudySectionCard {
        DetailTextField(label = "담당 선생님", value = application.teacherName ?: "확인할 수 없음")
        DetailTextField(label = "유형", value = application.typeName ?: "확인할 수 없음")
        ReadOnlyCalendar(dates = application.dates)
        DetailReason(value = application.reason ?: "확인할 수 없음")
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun DetailTextField(label: String, value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = label, color = DmsTheme.colors.inverseOnSurface, style = DmsTypography.BodyB)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DmsTheme.colors.onSurface, RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            Text(text = value, color = DmsTheme.colors.tertiaryContainer, style = DmsTypography.BodyM)
        }
    }
}

@Composable
private fun DetailReason(value: String) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = "사유", color = DmsTheme.colors.inverseOnSurface, style = DmsTypography.BodyB)
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(DmsTheme.colors.onSurface, RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Text(text = value, color = DmsTheme.colors.tertiaryContainer, style = DmsTypography.BodyM)
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun ReadOnlyCalendar(dates: List<String>) {
    val parsedDates = remember(dates) { dates.mapNotNull { runCatching { LocalDate.parse(it) }.getOrNull() }.toSet() }
    val initialMonth = parsedDates.minOrNull()?.let { CalendarYearMonth(it.year, it.monthNumber) }
        ?: run {
            val today = kotlin.time.Clock.System.now()
                .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()).date
            CalendarYearMonth(today.year, today.monthNumber)
        }
    var currentMonth by remember(initialMonth) { mutableStateOf(initialMonth) }
    val calendarDates = remember(currentMonth) { buildDetailCalendarDates(currentMonth) }

    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Text(text = "일정", color = DmsTheme.colors.inverseOnSurface, style = DmsTypography.BodyB)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            DmsButton(
                text = "‹",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = { currentMonth = currentMonth.minusMonths(1) },
            )
            Text(
                text = "${currentMonth.year} ${currentMonth.monthNumber}월",
                color = DmsTheme.colors.tertiaryContainer,
                style = DmsTypography.BodyM,
                modifier = Modifier.weight(1f),
            )
            DmsButton(
                text = "›",
                buttonType = ButtonType.Text,
                buttonColor = ButtonColor.Gray,
                onClick = { currentMonth = currentMonth.plusMonths(1) },
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("일", "월", "화", "수", "목", "금", "토").forEachIndexed { index, day ->
                Text(
                    text = day,
                    color = when (index) {
                        0 -> DmsTheme.colors.onError
                        6 -> DmsTheme.colors.onPrimary
                        else -> DmsTheme.colors.tertiaryContainer
                    },
                    style = DmsTypography.BodyM,
                    modifier = Modifier.size(32.dp),
                )
            }
        }
        calendarDates.chunked(7).forEach { week ->
            Row(modifier = Modifier.fillMaxWidth()) {
                week.forEach { item ->
                    val selected = item.date in parsedDates
                    Box(
                        modifier = Modifier.weight(1f).height(44.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(if (selected) 32.dp else 0.dp)
                                .background(DmsTheme.colors.onPrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center,
                        ) {
                            if (selected) {
                                Text(text = item.date.dayOfMonth.toString(), color = Color.White, style = DmsTypography.BodyM)
                            }
                        }
                        if (!selected) {
                            Text(
                                text = item.date.dayOfMonth.toString(),
                                color = detailCalendarColor(item.date, item.isCurrentMonth),
                                style = DmsTypography.BodyM,
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class DetailCalendarDate(val date: LocalDate, val isCurrentMonth: Boolean)

private fun buildDetailCalendarDates(month: CalendarYearMonth): List<DetailCalendarDate> {
    val first = month.atDay(1)
    val offset = when (first.dayOfWeek) {
        DayOfWeek.SUNDAY -> 0
        DayOfWeek.MONDAY -> 1
        DayOfWeek.TUESDAY -> 2
        DayOfWeek.WEDNESDAY -> 3
        DayOfWeek.THURSDAY -> 4
        DayOfWeek.FRIDAY -> 5
        DayOfWeek.SATURDAY -> 6
    }
    val previous = month.minusMonths(1)
    val result = buildList {
        repeat(offset) { add(DetailCalendarDate(previous.atDay(previous.lengthOfMonth() - offset + it + 1), false)) }
        for (day in 1..month.lengthOfMonth()) add(DetailCalendarDate(month.atDay(day), true))
        var nextDay = 1
        while (size % 7 != 0) add(DetailCalendarDate(month.plusMonths(1).atDay(nextDay++), false))
    }
    return result
}

@Composable
private fun detailCalendarColor(date: LocalDate, isCurrentMonth: Boolean) = when {
    !isCurrentMonth -> DmsTheme.colors.inverseSurface
    date.dayOfWeek == DayOfWeek.SUNDAY -> DmsTheme.colors.onError
    date.dayOfWeek == DayOfWeek.SATURDAY -> DmsTheme.colors.onPrimary
    date.dayOfWeek == DayOfWeek.FRIDAY -> DmsTheme.colors.onSurfaceVariant
    else -> DmsTheme.colors.tertiaryContainer
}

