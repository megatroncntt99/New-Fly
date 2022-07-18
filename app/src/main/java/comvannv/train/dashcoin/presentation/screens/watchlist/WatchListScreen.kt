package comvannv.train.dashcoin.presentation.screens.watchlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import comvannv.train.dashcoin.domain.model.CoinById
import comvannv.train.dashcoin.domain.model.toCoins
import comvannv.train.dashcoin.navigation.routes.Screens
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.components.TopBar
import comvannv.train.dashcoin.presentation.components.WatchListItem
import comvannv.train.dashcoin.presentation.screens.coins.CoinsViewModel
import comvannv.train.dashcoin.presentation.ui.theme.CustomGreen
import comvannv.train.dashcoin.presentation.ui.theme.DarkGray
import comvannv.train.dashcoin.presentation.ui.theme.LighterGray

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */

@Composable
fun WatchListScreen(
    watchViewModel: WatchListViewModel = hiltViewModel(),
    navController: NavController
) {
    val watchListState = watchViewModel.watchListState.value
    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()
            .padding(12.dp)
            .padding(bottom = 45.dp)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(title = "Watch List")
            Divider(color = LighterGray, modifier = Modifier.padding(bottom = 10.dp))
            LazyColumn {
                items(watchListState.result ?: emptyList()) { coin ->
                    WatchListItem(coin = coin.toCoins()) {
                        navController.navigate(Screens.CoinDetailScreen.route + "/${coin.id}")
                    }
                }
            }
        }
        if (watchListState.state == RequestState.NON) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = CustomGreen
            )
        }
        if (watchListState.state == RequestState.ERROR) {
            Text(
                text = watchListState.message.orEmpty(), color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}