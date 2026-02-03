package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.button.ButtonColor
import team.aliens.dms.kmp.core.designsystem.button.ButtonType
import team.aliens.dms.kmp.core.designsystem.button.DmsButton
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.snackbar.DmsSnackBarType
import team.aliens.dms.kmp.core.model.image.GalleryImageModel
import team.aliens.dms.kmp.core.ui.permission.RequestGalleryPermission
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileSideEffect
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileState
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileViewModel

@Composable
internal fun SelectProfile(
    onBackPressed: () -> Unit,
    onImageSelected: (imageId: String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SelectProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SelectProfileSideEffect.ImageSelected -> onImageSelected(effect.id)
                is SelectProfileSideEffect.ShowError -> onShowSnackBar(
                    DmsSnackBarType.ERROR,
                    effect.message,
                )
            }
        }
    }

    RequestGalleryPermission(
        onPermissionGranted = { viewModel.loadImagesIfNeeded() },
        onPermissionDenied = { onShowSnackBar(DmsSnackBarType.ERROR, "갤러리 권한이 필요합니다") },
    ) {
        SelectProfileScreen(
            state = state,
            onBackPressed = onBackPressed,
            onImageClick = viewModel::selectImage,
            onConfirmClick = viewModel::confirmSelection,
        )
    }
}

@Composable
private fun SelectProfileScreen(
    state: SelectProfileState,
    onBackPressed: () -> Unit,
    onImageClick: (String) -> Unit,
    onConfirmClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        DmsTopAppBar(
            onBackPressed = onBackPressed,
            actions = {
                DmsButton(
                    onClick = onConfirmClick,
                    text = "선택",
                    buttonType = ButtonType.Text,
                    buttonColor = ButtonColor.Primary,
                    enabled = state.buttonEnabled,
                )
            },
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(4.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                items = state.imageList,
                key = { it.id },
            ) { image ->
                ImageItem(
                    image = image,
                    isSelected = state.selectedId == image.id,
                    onImageClick = { onImageClick(image.id) },
                )
            }
        }
    }
}

@Composable
private fun ImageItem(
    image: GalleryImageModel,
    isSelected: Boolean,
    onImageClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onImageClick),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = image.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(3.dp, DmsTheme.colors.primary),
            )
        }
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = DmsTheme.colors.primary,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(24.dp)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    ),
            )
        }
    }
}
