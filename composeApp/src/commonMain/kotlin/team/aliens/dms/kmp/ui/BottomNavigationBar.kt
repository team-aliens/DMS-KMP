package team.aliens.dms.kmp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = Modifier.fillMaxHeight(0.08f),
        contentColor = DmsTheme.colors.onBackground,
        containerColor = DmsTheme.colors.onBackground,
    ) {
        bottomMenus.forEach {
            val selected = selectedRoute == it.route
            val color by animateColorAsState(
                targetValue = if (selected) {
                    DmsTheme.colors.surfaceContainerLow
                } else {
                    DmsTheme.colors.surfaceVariant
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
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(resource = if (selected) it.selectedIcon else it.icon),
                            contentDescription = it.route,
                            tint = color,
                        )
                        DmsText(
                            text = it.title,
                            style = DmsTypography.Body2Medium,
                            color = color,
                        )
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = DmsTheme.colors.surfaceContainerLow,
                    selectedTextColor = DmsTheme.colors.surfaceContainerLow,
                    selectedIndicatorColor = DmsTheme.colors.onBackground,
                    unselectedIconColor = DmsTheme.colors.surfaceVariant,
                    unselectedTextColor = DmsTheme.colors.surfaceVariant,
                    disabledIconColor = DmsTheme.colors.surfaceVariant,
                    disabledTextColor = DmsTheme.colors.surfaceVariant,
                ),
            )
        }
    }
}
