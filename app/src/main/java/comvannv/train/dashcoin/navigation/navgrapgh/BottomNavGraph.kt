package comvannv.train.dashcoin.navigation.navgrapgh

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import comvannv.train.dashcoin.navigation.routes.Screens
import comvannv.train.dashcoin.presentation.screens.coins.CoinsScreen
import comvannv.train.dashcoin.presentation.screens.news.NewsScreen
import comvannv.train.dashcoin.presentation.screens.watchlist.WatchListScreen

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
            WatchListScreen(navController = navController)
        }
        composable(route = Screens.CoinsNew.route) {
            NewsScreen()
        }
        composable(route = Screens.CoinDetailScreen.route + "/{coinId}") {
            Text(text = Screens.CoinDetailScreen.title)
        }
    }

}