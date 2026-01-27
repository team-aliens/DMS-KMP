package team.aliens.dms.kmp.feature.setting.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dmskmp.core.design_system.generated.resources.Res
import dmskmp.core.design_system.generated.resources.img_calendar
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.button.DmsItemButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.feature.setting.ui.component.SettingRotateContent
import team.aliens.dms.kmp.feature.setting.viewmodel.SettingSideEffect
import team.aliens.dms.kmp.feature.setting.viewmodel.SettingViewModel

@Composable
internal fun Setting(
    onBackPressed: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    onNavigateSelectProfile: () -> Unit,
    onNavigateSignIn: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SettingViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val (shouldShowSignOutDialog, onShouldShowSignOutDialogChange) = remember {
        mutableStateOf(false)
    }
    val (shouldShowWithdrawDialog, onShouldShowWithdrawDialogChange) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect {
            when (it) {
                SettingSideEffect.CannotFetchNotificationStatus -> {
                    onShowSnackBar(DmsSnackBarType.ERROR, "알림 상태 조회를 실패했어요")
                }
                SettingSideEffect.SignOutSuccess -> onNavigateSignIn()
                SettingSideEffect.WithdrawSuccess -> onNavigateSignIn()
            }
        }
    }

    if (shouldShowSignOutDialog) {
        AlertDialog(
            title = { Text(text = "로그아웃", style = DmsTypography.STitleM) },
            text = { Text(text = "사용자 정보가 기기에서 지워집니다. 정말 로그아웃 하시겠습니까?", style = DmsTypography.BodyM) },
            onDismissRequest = { onShouldShowSignOutDialogChange(false) },
            confirmButton = {
                DmsButton(
                    text = "확인",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = viewModel::signOut,
                )
            },
            dismissButton = {
                DmsButton(
                    text = "취소",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = { onShouldShowSignOutDialogChange(false) },
                )
            },
        )
    }

    if (shouldShowWithdrawDialog) {
        AlertDialog(
            title = { Text(text = "회원 탈퇴", style = DmsTypography.STitleM) },
            text = { Text(text = "회원 탈퇴 시 복구할 수 없습니다. 정말 탈퇴하시겠습니까?", style = DmsTypography.BodyM) },
            onDismissRequest = { onShouldShowWithdrawDialogChange(false) },
            confirmButton = {
                DmsButton(
                    text = "확인",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = viewModel::withdraw,
                )
            },
            dismissButton = {
                DmsButton(
                    text = "취소",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    onClick = { onShouldShowWithdrawDialogChange(false) },
                )
            },
        )
    }

    SettingScreen(
        rotated = state.isOnNotification,
        onNavigateResetPassword = onNavigateResetPassword,
        onNavigateSelectProfile = onNavigateSelectProfile,
        onNotificationClick = { viewModel.updateNotificationStatus(state.isOnNotification) },
        onShowSignOutDialogChange = { onShouldShowSignOutDialogChange(true) },
        onShowWithdrawDialogChange = { onShouldShowWithdrawDialogChange(true) },
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun SettingScreen(
    rotated: Boolean,
    onNavigateResetPassword: () -> Unit,
    onNavigateSelectProfile: () -> Unit,
    onNotificationClick: () -> Unit,
    onShowSignOutDialogChange: () -> Unit,
    onShowWithdrawDialogChange: () -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed,
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DmsTheme.colors.background)
                .padding(horizontal = 10.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            DmsItemButton(
                iconRes = Res.drawable.img_3d_key,
                text = "비밀번호 재설정",
                onClick = onNavigateResetPassword,
            )
            DmsItemButton(
                iconRes = Res.drawable.img_calendar,
                text = "프로필 사진 변경",
                onClick = onNavigateSelectProfile,
            )
            SettingRotateContent(
                iconRes = Res.drawable.img_repeat,
                text = "푸시 알림 켜기",
                rotated = rotated,
                onClick = onNotificationClick,
            )
            DmsItemButton(
                iconRes = Res.drawable.img_3d_out,
                text = "로그아웃",
                onClick = onShowSignOutDialogChange,
            )
            DmsItemButton(
                iconRes = Res.drawable.img_3d_out,
                text = "회원 탈퇴",
                onClick = onShowWithdrawDialogChange,
            )
        }
    }
}
