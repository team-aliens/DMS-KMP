package team.aliens.dms.kmp.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTheme
import team.aliens.dms.kmp.core.designsystem.foundation.DmsTypography
import team.aliens.dms.kmp.core.designsystem.text.DmsText
import team.aliens.dms.kmp.util.isRouteInHierarchy

private val bottomMenus =
    listOf(
        BottomMenu.Home,
        BottomMenu.Application,
        BottomMenu.MyPage,
    )

@Composable
fun BottomNavigationBar(navController: NavController = rememberNavController()) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination

    BottomAppBar(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        containerColor = DmsTheme.colors.surfaceTint,
    ) {
        bottomMenus.forEach { destination ->
            val selected = currentDestination.isRouteInHierarchy(destination.route::class)
            val color by animateColorAsState(
                targetValue =
                    if (selected) {
                        DmsTheme.colors.inverseOnSurface
                    } else {
                        DmsTheme.colors.scrim
                    },
            )

            NavigationBarItem(
                selected = selected,
                enabled = !selected,
                onClick = {
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(resource = if (selected) destination.selectedIcon else destination.icon),
                            contentDescription = destination.title,
                            tint = color,
                        )
                        DmsText(
                            text = destination.title,
                            style = DmsTypography.labelB,
                            color = color,
                        )
                    }
                },
                colors =
                    NavigationBarItemColors(
                        selectedIconColor = DmsTheme.colors.inverseOnSurface,
                        selectedTextColor = DmsTheme.colors.inverseOnSurface,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = DmsTheme.colors.scrim,
                        unselectedTextColor = DmsTheme.colors.scrim,
                        disabledIconColor = DmsTheme.colors.scrim,
                        disabledTextColor = DmsTheme.colors.scrim,
                    ),
            )
        }
    }
}
