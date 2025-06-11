package team.aliens.dms.kmp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.DmsIconButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.modifier.DmsShadowType
import team.aliens.dms.kmp.core.designsystem.modifier.dmsShadowModifier
import team.aliens.dms.kmp.feature.home.ui.component.AnnouncementCard
import team.aliens.dms.kmp.feature.home.ui.component.MealContent
import team.aliens.dms.kmp.feature.home.viewmodel.HomeState
import team.aliens.dms.kmp.feature.home.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Home() {
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    val (shouldShowCalendar, onShouldShowCalendarChange) = remember { mutableStateOf(false) }

    if (shouldShowCalendar) {
        ModalBottomSheet(
            onDismissRequest = {
                onShouldShowCalendarChange(false)
            },
        ) {
//            DmsCalendar(
//                modifier = Modifier.fillMaxWidth(),
//                selectedDate = uiState.selectedDate,
//                onSelectedDateChange = onSelectedDateChange,
//            )
        }
    }
    HomeScreen(
        state = state,
        onDateChange = viewModel::updateDate,
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onDateChange: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            showLogo = true,
            actions = {
                DmsIconButton(
                    resource = DmsIcon.Notification,
                    tint = DmsTheme.colors.inversePrimary,
                    size = 28.dp,
                    onClick = { },
                )
            },
        )
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            val brush = Brush.verticalGradient(
                listOf(
                    Color.White.copy(alpha = 0.03f),
                    Color(0xFF3D8AFF).copy(alpha = 0.15f),
                ),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(brush)
                    .align(Alignment.BottomCenter),
            )
            AnnouncementCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .dmsShadowModifier(DmsShadowType.Light20)
                    .align(Alignment.TopCenter),
                onClick = { },
            )
            MealContent(
                modifier = Modifier.align(Alignment.Center),
                onNextDay = { onDateChange(state.selectedDate.plus(DatePeriod(days = 1))) },
                onPreviousDay = { onDateChange(state.selectedDate.minus(DatePeriod(days = 1))) },
                selectDate = state.selectedDate,
                meal = state.meal,
            )
        }
    }
}

@Composable
private fun DateCard(
    modifier: Modifier = Modifier,
    onNextDay: () -> Unit,
    onPreviousDay: () -> Unit,
    selectDate: LocalDate,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 36.dp,
                vertical = 14.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        DmsIconButton(
            resource = DmsIcon.Backward,
            tint = DmsTheme.colors.surfaceContainerLow,
            size = 18.dp,
            onClick = onPreviousDay,
        )
//        DmsText(
//            modifier = Modifier
//                .clip(RoundedCornerShape(8.dp))
//                .border(
//                    width = 1.dp,
//                    color = DmsTheme.colors.onSurface,
//                    shape = RoundedCornerShape(8.dp),
//                )
//                .clickable(
//                    onClick = { },
//                )
//                .padding(
//                    horizontal = 14.dp,
//                    vertical = 8.dp,
//                ),
//            text = "${selectDate.monthNumber}월 ${selectDate.dayOfMonth}일 ${selectDate.dayOfWeek.text}요일",
//            color = DmsTheme.colors.surfaceContainerLow,
//            style = DmsTypography.Body1SemiBold,
//      )
        DmsIconButton(
            resource = DmsIcon.Forward,
            tint = DmsTheme.colors.surfaceContainerLow,
            size = 18.dp,
            onClick = onNextDay,
        )
    }
}
