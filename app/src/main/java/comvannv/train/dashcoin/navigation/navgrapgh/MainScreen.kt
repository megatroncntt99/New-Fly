package comvannv.train.dashcoin.navigation.navgrapgh

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import comvannv.train.dashcoin.navigation.routes.Screens
import comvannv.train.dashcoin.presentation.ui.theme.LighterGray
import comvannv.train.dashcoin.presentation.ui.theme.TextWhite

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when (navBackStackEntry?.destination?.route) {
        Screens.CoinDetailScreen.route + "/{coinId}" -> bottomBarState.value = false
        else -> bottomBarState.value = true
    }
    Scaffold (
        bottomBar ={
            BottomBar(
                navController = navController,
                state = bottomBarState
            )
        },
    )
    {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    state: MutableState<Boolean>
) {
    val screens = listOf(Screens.CoinsScreen, Screens.CoinWatchList, Screens.CoinsNew)
    AnimatedVisibility(
        visible = state.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(backgroundColor = LighterGray) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            screens.forEach { screen ->
                BottomNavigationItem(
                    selected = currentRoute == screen.route, onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = screen.title)
                    },
                    icon = {
                        Icon(imageVector = screen.icon, contentDescription = null)
                    },
                    alwaysShowLabel = true,
                    selectedContentColor = TextWhite,
                    unselectedContentColor = TextWhite.copy(alpha = ContentAlpha.disabled)
                )
            }
        }

    }
}
