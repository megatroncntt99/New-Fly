package comvannv.train.dashcoin.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
sealed class Screens(val route: String, val title: String, val icon: ImageVector) {
    object CoinsScreen : Screens("CoinScreen", "Home", Icons.Default.Home)
    object CoinWatchList : Screens("CoinWatchList", "CoinWatch", Icons.Default.Favorite)
    object CoinsNew : Screens("CoinsNew", "News", Icons.Default.List)
    object CoinDetailScreen: Screens("CoinDetailScreen", "News", Icons.Default.List)
}