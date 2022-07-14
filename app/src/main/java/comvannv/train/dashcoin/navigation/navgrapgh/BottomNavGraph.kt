package comvannv.train.dashcoin.navigation.navgrapgh

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import comvannv.train.dashcoin.navigation.routes.Screens
import comvannv.train.dashcoin.presentation.screens.coins.CoinsScreen

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.CoinsScreen.route) {
        composable(route = Screens.CoinsScreen.route) {
            CoinsScreen(navController = navController)
        }
        composable(route = Screens.CoinWatchList.route) {
            Text(text = Screens.CoinWatchList.title)
        }
        composable(route = Screens.CoinsNew.route) {
            Text(text = Screens.CoinsNew.title)
        }
        composable(route = Screens.CoinDetailScreen.route) {
            Text(text = Screens.CoinDetailScreen.title)
        }
    }

}