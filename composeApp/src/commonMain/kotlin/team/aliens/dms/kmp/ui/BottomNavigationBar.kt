package team.aliens.dms.kmp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
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

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DmsTheme.colors.surface,
        )
        BottomAppBar(
            modifier = Modifier
                .fillMaxHeight(0.08f),
            contentColor = DmsTheme.colors.background,
            containerColor = DmsTheme.colors.background,
        ) {
            bottomMenus.forEach {
                val selected = selectedRoute == it.route
                val color by animateColorAsState(
                    targetValue = if (selected) {
                        DmsTheme.colors.inversePrimary
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
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Icon(
                                painter = painterResource(resource = if (selected) it.selectedIcon else it.icon),
                                contentDescription = it.title,
                                tint = color,
                            )
                            DmsText(
                                text = it.title,
                                style = DmsTypography.Body3,
                                color = color,
                            )
                        }
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = DmsTheme.colors.inversePrimary,
                        selectedTextColor = DmsTheme.colors.inversePrimary,
                        selectedIndicatorColor = DmsTheme.colors.background,
                        unselectedIconColor = DmsTheme.colors.inverseSurface,
                        unselectedTextColor = DmsTheme.colors.inverseSurface,
                        disabledIconColor = DmsTheme.colors.inverseSurface,
                        disabledTextColor = DmsTheme.colors.inverseSurface,
                    ),
                )
            }
        }
    }
}
