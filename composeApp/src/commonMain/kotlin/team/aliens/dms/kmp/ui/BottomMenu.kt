package team.aliens.dms.kmp.ui

import org.jetbrains.compose.resources.DrawableResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.feature.application.navigation.NAVIGATION_APPLICATION
import team.aliens.dms.kmp.feature.home.navigation.NAVIGATION_HOME
import team.aliens.dms.kmp.feature.mypage.navigation.NAVIGATION_MY_PAGE
import team.aliens.dms.kmp.feature.notice.navigation.NAVIGATION_NOTICE

sealed class BottomMenu(
    val route: String,
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: String,
) {
    data object Home : BottomMenu(
        route = NAVIGATION_HOME,
        icon = DmsIcon.Home,
        selectedIcon = DmsIcon.HomeFill,
        title = "홈",
    )

    data object Application : BottomMenu(
        route = NAVIGATION_APPLICATION,
        icon = DmsIcon.AddNotes,
        selectedIcon = DmsIcon.AddNotesFill,
        title = "신청",
    )

    data object Notice : BottomMenu(
        route = NAVIGATION_NOTICE,
        icon = DmsIcon.BreakingNews,
        selectedIcon = DmsIcon.BreakingNewsFill,
        title = "안내",
    )

    data object MyPage : BottomMenu(
        route = NAVIGATION_MY_PAGE,
        icon = DmsIcon.Person,
        selectedIcon = DmsIcon.PersonFill,
        title = "내 페이지",
    )
}
