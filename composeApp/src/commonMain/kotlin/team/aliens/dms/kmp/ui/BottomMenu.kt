package team.aliens.dms.kmp.ui

import org.jetbrains.compose.resources.DrawableResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsIcon
import team.aliens.dms.kmp.feature.application.navigation.ApplicationRoute
import team.aliens.dms.kmp.feature.home.navigation.HomeRoute
import team.aliens.dms.kmp.feature.mypage.navigation.MyPageRoute

sealed class BottomMenu(
    val route: Any,
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val title: String,
) {
    data object Home : BottomMenu(
        route = HomeRoute,
        icon = DmsIcon.Home,
        selectedIcon = DmsIcon.HomeFill,
        title = "홈",
    )

    data object Application : BottomMenu(
        route = ApplicationRoute,
        icon = DmsIcon.CheckCircle,
        selectedIcon = DmsIcon.CheckCircleFill,
        title = "신청",
    )

    data object MyPage : BottomMenu(
        route = MyPageRoute,
        icon = DmsIcon.Person,
        selectedIcon = DmsIcon.PersonFill,
        title = "마이페이지",
    )
}
