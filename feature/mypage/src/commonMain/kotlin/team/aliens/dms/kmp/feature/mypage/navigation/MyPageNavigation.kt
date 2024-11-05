package team.aliens.dms.kmp.feature.mypage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import team.aliens.dms.kmp.feature.mypage.ui.MyPage

const val NAVIGATION_MY_PAGE = "myPage"

fun NavGraphBuilder.myPage() {
    composable(NAVIGATION_MY_PAGE) {
        MyPage()
    }
}
