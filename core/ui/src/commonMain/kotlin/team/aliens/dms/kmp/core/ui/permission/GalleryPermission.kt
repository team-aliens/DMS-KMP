package team.aliens.dms.kmp.core.ui.permission

import androidx.compose.runtime.Composable

@Composable
expect fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
)
