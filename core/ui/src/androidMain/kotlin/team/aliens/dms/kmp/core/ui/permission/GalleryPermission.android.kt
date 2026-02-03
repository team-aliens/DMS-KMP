package team.aliens.dms.kmp.core.ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.gallery.GALLERY

@Composable
actual fun RequestGalleryPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    content: @Composable () -> Unit,
) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) { factory.createPermissionsController() }

    BindEffect(controller)

    LaunchedEffect(Unit) {
        val isGranted = controller.isPermissionGranted(Permission.GALLERY)
        if (isGranted) {
            onPermissionGranted()
        } else {
            try {
                controller.providePermission(Permission.GALLERY)
                onPermissionGranted()
            } catch (e: Exception) {
                onPermissionDenied()
            }
        }
    }

    content()
}
