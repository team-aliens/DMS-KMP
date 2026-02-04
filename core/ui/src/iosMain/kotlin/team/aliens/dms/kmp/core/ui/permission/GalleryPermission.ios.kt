package team.aliens.dms.kmp.core.ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    // iOS는 PHPhotoLibrary 권한을 앱 최초 접근 시 시스템이 자동으로 요청
    // 또는 moko-permissions 라이브러리 사용 가능
    LaunchedEffect(Unit) {
        onPermissionGranted()
    }
    content()
}
