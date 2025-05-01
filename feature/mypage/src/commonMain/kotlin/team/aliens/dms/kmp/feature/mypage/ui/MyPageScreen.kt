package team.aliens.dms.kmp.feature.mypage.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import team.aliens.dms.kmp.core.designsystem.appbar.DmsTopAppBar
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.feature.mypage.component.OptionsContent
import team.aliens.dms.kmp.feature.mypage.component.PhraseCard
import team.aliens.dms.kmp.feature.mypage.component.PointCards
import team.aliens.dms.kmp.feature.mypage.component.ProfileContent
import team.aliens.dms.kmp.feature.mypage.viewmodel.MyPageState
import team.aliens.dms.kmp.feature.mypage.viewmodel.MyPageViewModel

@Composable
internal fun MyPage() {
    val viewModel: MyPageViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    MyPageScreen(
        state = state,
    )
}

@Composable
private fun MyPageScreen(
    state: MyPageState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DmsTheme.colors.background),
    ) {
        DmsTopAppBar(
            title = "마이페이지",
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(64.dp),
        ) {
            ProfileContent(
                gcn = state.myPage.gcn,
                name = state.myPage.name,
                schoolName = state.myPage.schoolName,
                genderType = state.myPage.sex,
                profileImageUrl = state.myPage.profileImageUrl,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                PhraseCard(
                    phrase = state.myPage.phrase,
                )
                PointCards(
                    bonusPoint = state.myPage.bonusPoint,
                    minusPoint = state.myPage.minusPoint,
                )
                OptionsContent()
            }
        }
    }
}
