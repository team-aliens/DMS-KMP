package team.aliens.dms.kmp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText

private val bottomMenus = listOf(
    BottomMenu.Home,
    BottomMenu.Application,
    BottomMenu.Notice,
    BottomMenu.MyPage,
)

@Composable
fun BottomNavigationBar(
    navController: NavController = rememberNavController(),
) {
    val selectedRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    BottomAppBar(
        modifier = Modifier
            .fillMaxHeight(0.08f)
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                )
                shadowElevation = 20f
            },
        contentColor = DmsTheme.colors.onBackground,
        containerColor = DmsTheme.colors.onBackground,
    ) {
        bottomMenus.forEach {
            val selected = selectedRoute == it.route
            val color by animateColorAsState(
                targetValue = if (selected) {
                    DmsTheme.colors.onBackground
                } else {
                    DmsTheme.colors.inverseSurface
                },
            )

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(it.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Column {
                        Icon(
                            painter = painterResource(resource = if (selected) it.selectedIcon else it.icon),
                            contentDescription = it.route,
                            tint = color,
                        )
                        DmsText(
                            text = it.title,
                            style = DmsTypography.Button4,
                            color = color,
                        )
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = DmsTheme.colors.onBackground,
                    selectedTextColor = DmsTheme.colors.onBackground,
                    selectedIndicatorColor = DmsTheme.colors.onBackground,
                    unselectedIconColor = DmsTheme.colors.inverseSurface,
                    unselectedTextColor = DmsTheme.colors.inverseSurface,
                    disabledIconColor = DmsTheme.colors.inverseSurface,
                    disabledTextColor = DmsTheme.colors.inverseSurface,
                ),
            )
        }
    }
}
