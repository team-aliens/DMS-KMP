package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import me.saket.telephoto.zoomable.ZoomableState
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.feature.profile.viewmodel.AdjustProfileSideEffect
import team.aliens.dms.kmp.feature.profile.viewmodel.AdjustProfileState
import team.aliens.dms.kmp.feature.profile.viewmodel.AdjustProfileViewModel

@Composable
internal fun AdjustProfile(
    onBackPressed: () -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: AdjustProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val zoomableState = rememberZoomableState()

    LaunchedEffect(zoomableState.contentTransformation) {
        val transformation = zoomableState.contentTransformation
        viewModel.updateZoomState(
            scale = transformation.scale.scaleX,
            offsetX = transformation.offset.x,
            offsetY = transformation.offset.y,
        )
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is AdjustProfileSideEffect.ProfileImageSet -> onBackPressed()
                is AdjustProfileSideEffect.ProfileImageBadRequest -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    "프로필 이미지 변경에 실패했습니다",
                )
            }
        }
    }

    AdjustProfileScreen(
        state = state,
        zoomableState = zoomableState,
        onBackPressed = onBackPressed,
        updateProfileImage = viewModel::uploadCroppedProfile,
    )
}

@Composable
private fun AdjustProfileScreen(
    state: AdjustProfileState,
    zoomableState: ZoomableState,
    onBackPressed: () -> Unit,
    updateProfileImage: () -> Unit,
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
                .padding(top = 68.dp)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(DmsTheme.colors.surface),
        ) {
            DmsText(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(horizontal = 16.dp),
                text = "이미지 조정",
                style = DmsTypography.BodyB,
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 24.dp)
                    .clip(RoundedCornerShape(32.dp)),
            ) {
                if (state.imageUri.isNotBlank()) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .zoomable(zoomableState),
                        model = state.imageUri,
                        contentScale = ContentScale.Crop,
                        contentDescription = "프로필 이미지",
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        DmsButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 12.dp),
            text = "변경하기",
            buttonType = ButtonType.Contained,
            buttonColor = ButtonColor.Primary,
            isLoading = state.isLoading,
            onClick = updateProfileImage,
        )
    }
}
