package team.aliens.dms.kmp.core.ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    // JVM desktop은 갤러리 권한 불필요
    LaunchedEffect(Unit) {
        onPermissionGranted()
    }
    content()
}
