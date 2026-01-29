package team.aliens.dms.kmp.feature.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import team.aliens.dms.kmp.core.ui.permission.RequestGalleryPermission
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileSideEffect
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileState
import team.aliens.dms.kmp.feature.profile.viewmodel.SelectProfileViewModel

@Composable
internal fun SelectProfile(
    onBackPressed: () -> Unit,
    onImageSelected: (String) -> Unit,
    onShowSnackBar: (DmsSnackBarType, String) -> Unit,
) {
    val viewModel: SelectProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SelectProfileSideEffect.ImageSelected -> onImageSelected(effect.uri)
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
    Scaffold(
        topBar = {
            DmsTopAppBar(
                title = "프로필 이미지 선택",
                onBackPressed = onBackPressed,
            )
        },
        bottomBar = {
            DmsButton(
                modifier = Modifier.fillMaxWidth(),
                text = "선택 완료",
                buttonType = ButtonType.Contained,
                buttonColor = ButtonColor.Primary,
                enabled = state.buttonEnabled,
                onClick = onConfirmClick,
            )
        },
        containerColor = DmsTheme.colors.surfaceTint,
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = state.uriList,
                key = { it },
            ) { uri ->
                ImageItem(
                    uri = uri,
                    isSelected = state.selectedUri == uri,
                    onImageClick = { onImageClick(uri) },
                )
            }
        }
    }
}

@Composable
private fun ImageItem(
    uri: String,
    isSelected: Boolean,
    onImageClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onImageClick),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = uri,
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
                    .align(Alignment.Center)
                    .size(32.dp)
                    .background(DmsTheme.colors.primary, CircleShape)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp),
                )
            }
        }
    }
}
